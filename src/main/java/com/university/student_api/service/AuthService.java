package com.university.student_api.service;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.university.student_api.entity.*;
import com.university.student_api.repository.*;
import com.university.student_api.util.JwtUtil;   

@Service
public class AuthService {

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private EducationalQualificationRepository qualificationRepository;

    @Autowired
    private EnrollmentRepository enrollmentRepository;

    @Autowired
    private CourseRepository courseRepository;

    @Autowired
    private CertificateRepository certificateRepository;

    @Autowired
    private DepartmentRepository departmentRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private RollNumberService rollNumberService;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Transactional
    public String register(String studentJson,
                           MultipartFile photo,
                           MultipartFile cert10,
                           MultipartFile cert12,
                           MultipartFile certIti,
                           MultipartFile certDiploma) throws IOException {

        // Parse student data
        Student student = objectMapper.readValue(studentJson, Student.class);

        // Check uniqueness
        if (studentRepository.findByAadhaarNumber(student.getAadhaarNumber()).isPresent()) {
            throw new RuntimeException("Aadhaar number already registered");
        }
        if (studentRepository.findByEmail(student.getEmail()).isPresent()) {
            throw new RuntimeException("Email already registered");
        }

        // Fetch department from the transient departmentCode
        if (student.getDepartmentCode() != null && !student.getDepartmentCode().isEmpty()) {
            Department department = departmentRepository.findById(student.getDepartmentCode())
                    .orElseThrow(() -> new RuntimeException("Department not found: " + student.getDepartmentCode()));
            student.setDepartment(department);
        } else {
            throw new RuntimeException("Department code is required");
        }

        // Encode password and generate roll number
        student.setPassword(passwordEncoder.encode(student.getPassword()));
        String rollNumber = rollNumberService.generateRollNumber(student.getDepartment().getCode());
        student.setRollNumber(rollNumber);
        student.setRole(Role.STUDENT);
        student.setFailedLoginAttempts(0);

        studentRepository.save(student);

        // Save educational qualification
        EducationalQualification qualification = new EducationalQualification();
        qualification.setStudentRollNumber(rollNumber);
        qualification.setQualificationType(student.getQualificationType());
        qualification.setTenthBoard(student.getTenthBoard());
        qualification.setTenthYear(student.getTenthYear());
        qualification.setTenthPercentage(student.getTenthPercentage());

        QualificationType qType = student.getQualificationType();
        if (qType == QualificationType.TWELFTH) {
            qualification.setTwelfthBoard(student.getTwelfthBoard());
            qualification.setTwelfthYear(student.getTwelfthYear());
            qualification.setTwelfthPercentage(student.getTwelfthPercentage());
            qualification.setTwelfthStream(student.getTwelfthStream());
        } else if (qType == QualificationType.ITI) {
            qualification.setItiTrade(student.getItiTrade());
            qualification.setItiYear(student.getItiYear());
            qualification.setItiPercentage(student.getItiPercentage());
        } else if (qType == QualificationType.DIPLOMA) {
            qualification.setDiplomaBranch(student.getDiplomaBranch());
            qualification.setDiplomaCredits(student.getDiplomaCredits());
            qualification.setDiplomaYear(student.getDiplomaYear());
            qualification.setDiplomaPercentage(student.getDiplomaPercentage());
        }
        qualification.setVerified(false);
        qualificationRepository.save(qualification);

        // Save photo (if needed)
        if (photo != null && !photo.isEmpty()) {
            // Optional: save to StudentPhoto table
        }

        // Save certificates
        if (cert10 != null && !cert10.isEmpty())
            saveCertificate(rollNumber, CertificateType.TENTH_MARK_SHEET, cert10);
        if (cert12 != null && !cert12.isEmpty())
            saveCertificate(rollNumber, CertificateType.TWELFTH_MARK_SHEET, cert12);
        if (certIti != null && !certIti.isEmpty())
            saveCertificate(rollNumber, CertificateType.ITI_CERTIFICATE, certIti);
        if (certDiploma != null && !certDiploma.isEmpty())
            saveCertificate(rollNumber, CertificateType.DIPLOMA_CERTIFICATE, certDiploma);

        // Enroll in selected courses
        if (student.getSelectedCourses() != null && !student.getSelectedCourses().isEmpty()) {
            for (String courseCode : student.getSelectedCourses()) {
                Course course = courseRepository.findById(courseCode).orElse(null);
                if (course != null) {
                    Enrollment enrollment = new Enrollment();
                    enrollment.setId(new EnrollmentId(courseCode, rollNumber));
                    enrollment.setEnrollmentDate(LocalDateTime.now());
                    enrollment.setCourse(course);
                    enrollment.setStudent(student);
                    enrollmentRepository.save(enrollment);
                }
            }
        }

        return "Registration successful! Your roll number is: " + rollNumber;
    }

    private void saveCertificate(String rollNumber, CertificateType type, MultipartFile file) throws IOException {
        Certificate certificate = new Certificate();
        certificate.setCertificateType(type);
        certificate.setFileName(file.getOriginalFilename());
        certificate.setContentType(file.getContentType());
        certificate.setFileData(file.getBytes());
        certificate.setUploadDate(LocalDateTime.now());
        certificate.setVerified(false);

        // Create a temporary student object with only roll number
        Student student = new Student();
        student.setRollNumber(rollNumber);
        certificate.setStudent(student);

        certificateRepository.save(certificate);
    }

    public Map<String, String> login(String rollNumber, String password) {
        Student student = studentRepository.findById(rollNumber)
                .orElseThrow(() -> new RuntimeException("Invalid credentials"));
        if (!passwordEncoder.matches(password, student.getPassword())) {
            throw new RuntimeException("Invalid credentials");
        }
        Map<String, String> response = new HashMap<>();
        response.put("token", jwtUtil.generateToken(rollNumber));
        response.put("role", student.getRole().name());
        return response;
    }
}