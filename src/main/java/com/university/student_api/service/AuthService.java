package com.university.student_api.service;

import com.university.student_api.dto.AuthResponse;
import com.university.student_api.dto.LoginRequest;
import com.university.student_api.dto.RegisterRequest;
import com.university.student_api.entity.*;
import com.university.student_api.repository.*;
import com.university.student_api.security.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Service
public class AuthService {

    @Autowired private StudentRepository studentRepository;
    @Autowired private DepartmentRepository departmentRepository;
    @Autowired private CourseRepository courseRepository;
    @Autowired private EnrollmentRepository enrollmentRepository;
    @Autowired private RollNumberSequenceRepository rollNumberSequenceRepository;
    @Autowired private EducationalQualificationRepository qualificationRepository;
    @Autowired private StudentPhotoRepository photoRepository;
    @Autowired private CertificateRepository certificateRepository;
    @Autowired private PasswordEncoder passwordEncoder;
    @Autowired private JwtUtil jwtUtil;
    @Autowired private AuthenticationManager authManager;

    // Helper method to save a certificate
    private void saveCertificate(String studentRollNumber, String type, MultipartFile file) throws IOException {
        if (file == null || file.isEmpty()) return;
        // Remove old certificate of the same type
        List<Certificate> existing = certificateRepository.findByStudentRollNumberAndCertificateType(studentRollNumber, type);
        certificateRepository.deleteAll(existing);
        Certificate cert = new Certificate();
        cert.setStudentRollNumber(studentRollNumber);
        cert.setCertificateType(type);
        cert.setFileData(file.getBytes());
        cert.setFileName(file.getOriginalFilename());
        cert.setContentType(file.getContentType());
        cert.setUploadDate(LocalDateTime.now());
        cert.setVerified(false);
        certificateRepository.save(cert);
    }

    @Transactional
    public String register(RegisterRequest dto,
                           MultipartFile photo,
                           MultipartFile cert10,
                           MultipartFile cert12,
                           MultipartFile certDiploma,
                           MultipartFile certIti) throws IOException {

        // 1. Check duplicate Aadhaar
        if (studentRepository.existsByAadhaarNumber(dto.getAadhaarNumber())) {
            throw new RuntimeException("Aadhaar number already registered");
        }

        // 2. Find department
        Department department = departmentRepository.findById(dto.getDepartmentCode())
                .orElseThrow(() -> new RuntimeException("Department not found"));

        // 3. Fetch selected courses
        List<Course> courses = courseRepository.findAllById(dto.getSelectedCourses());
        if (courses.size() != dto.getSelectedCourses().size()) {
            throw new RuntimeException("One or more selected courses do not exist");
        }

        // 4. Generate roll number
        RollNumberSequence seq = rollNumberSequenceRepository.findSequenceForUpdate()
                .orElse(new RollNumberSequence(1L, 1000L));
        long nextNumber = seq.getCurrentValue() + 1;
        seq.setCurrentValue(nextNumber);
        rollNumberSequenceRepository.save(seq);
        String rollNumber = dto.getDepartmentCode() + String.format("%04d", nextNumber);

        // 5. Calculate academic year (optional: based on qualification type)
        int academicYear = 1; // default
        // If you want to set based on qualification type, uncomment the following:
        // if ("DIPLOMA".equals(dto.getQualificationType())) academicYear = 2;

        // 6. Create and save student
        Student student = new Student();
        student.setRollNumber(rollNumber);
        student.setAadhaarNumber(dto.getAadhaarNumber());
        student.setName(dto.getName());
        student.setFullName(dto.getFullName());
        student.setAddress(dto.getAddress());
        student.setGender(dto.getGender());
        student.setDepartment(department);
        student.setSelectedCourse(courses.isEmpty() ? null : courses.get(0).getCode());
        student.setEmail(dto.getEmail());
        student.setAcademicYear(academicYear);
        student.setPassword(passwordEncoder.encode(dto.getPassword()));
        student.setRole("ROLE_STUDENT");

        studentRepository.save(student);

        // 7. Save educational qualifications
        if (dto.getTenthBoard() != null && !dto.getTenthBoard().isEmpty()) {
            EducationalQualification q = new EducationalQualification();
            q.setStudentRollNumber(rollNumber);
            q.setTenthBoard(dto.getTenthBoard());
            q.setTenthYear(dto.getTenthYear());
            q.setTenthPercentage(dto.getTenthPercentage());
            q.setQualificationType(dto.getQualificationType());

            if ("12TH".equals(dto.getQualificationType())) {
                q.setTwelfthBoard(dto.getTwelfthBoard());
                q.setTwelfthYear(dto.getTwelfthYear());
                q.setTwelfthPercentage(dto.getTwelfthPercentage());
                q.setTwelfthStream(dto.getTwelfthStream()); // NEW
            } else if ("DIPLOMA".equals(dto.getQualificationType())) {
                q.setDiplomaBranch(dto.getDiplomaBranch());
                q.setDiplomaCredits(dto.getDiplomaCredits());
                q.setDiplomaYear(dto.getDiplomaYear()); // NEW
                q.setDiplomaPercentage(dto.getDiplomaPercentage()); // NEW
            } else if ("ITI".equals(dto.getQualificationType())) {
                q.setItiTrade(dto.getItiTrade());
                q.setItiYear(dto.getItiYear()); // NEW
                q.setItiPercentage(dto.getItiPercentage()); // NEW
            }
            q.setVerified(false);
            qualificationRepository.save(q);
        }

        // 8. Save profile photo if provided
        if (photo != null && !photo.isEmpty()) {
            StudentPhoto photoEntity = new StudentPhoto();
            photoEntity.setStudentRollNumber(rollNumber);
            photoEntity.setImageData(photo.getBytes());
            photoEntity.setContentType(photo.getContentType());
            photoRepository.save(photoEntity);
        }

        // 9. Save certificates
        saveCertificate(rollNumber, "10TH", cert10);
        saveCertificate(rollNumber, "12TH", cert12);
        saveCertificate(rollNumber, "DIPLOMA", certDiploma);
        saveCertificate(rollNumber, "ITI", certIti);

        // 10. Create enrollments for each selected course
        for (Course course : courses) {
            Enrollment enrollment = new Enrollment();
            EnrollmentId id = new EnrollmentId(rollNumber, course.getCode());
            enrollment.setId(id);
            enrollment.setStudent(student);
            enrollment.setCourse(course);
            enrollment.setEnrollmentDate(LocalDateTime.now());
            enrollmentRepository.save(enrollment);
        }

        return "Registered successfully! Your roll number: " + rollNumber;
    }

    public AuthResponse login(LoginRequest dto) {
        Student student = studentRepository.findById(dto.getRollNumber())
                .orElseThrow(() -> new RuntimeException("User not found"));

        // Lock logic
        if (student.getFailedLoginAttempts() >= 3 && student.getLastFailedLoginTime() != null) {
            long minutes = ChronoUnit.MINUTES.between(student.getLastFailedLoginTime(), LocalDateTime.now());
            if (minutes < 5) {
                throw new RuntimeException("Account locked. Try again after " + (5 - minutes) + " minutes.");
            }
            // Auto-unlock after 5 min
            student.setFailedLoginAttempts(0);
            student.setLastFailedLoginTime(null);
            studentRepository.save(student);
        }

        try {
            authManager.authenticate(
                    new UsernamePasswordAuthenticationToken(dto.getRollNumber(), dto.getPassword())
            );

            // Success → reset counter
            student.setFailedLoginAttempts(0);
            student.setLastFailedLoginTime(null);
            studentRepository.save(student);

            String token = jwtUtil.generateToken(dto.getRollNumber());
            return new AuthResponse(token, student.getRole().replace("ROLE_", ""));

        } catch (BadCredentialsException ex) {
            student.setFailedLoginAttempts(student.getFailedLoginAttempts() + 1);
            student.setLastFailedLoginTime(LocalDateTime.now());
            studentRepository.save(student);

            int attemptsLeft = 3 - student.getFailedLoginAttempts();
            throw new RuntimeException("Invalid credentials. " + (attemptsLeft > 0 ?
                    attemptsLeft + " attempts left." : "Account locked for 5 minutes."));
        }
    }
}