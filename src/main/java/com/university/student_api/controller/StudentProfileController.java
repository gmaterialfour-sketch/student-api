package com.university.student_api.controller;

import com.university.student_api.entity.*;
import com.university.student_api.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.security.Principal;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/student")
public class StudentProfileController {

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private EnrollmentRepository enrollmentRepository;

    @Autowired
    private AttendanceRepository attendanceRepository;

    @Autowired
    private EducationalQualificationRepository qualificationRepository;

    @Autowired
    private CertificateRepository certificateRepository;

    @Autowired
    private StudentPhotoRepository photoRepository;

    // ---------- Enrollments ----------
    @GetMapping("/enrollments")
    public List<Enrollment> getMyEnrollments(Principal principal) {
        String rollNumber = principal.getName();
        return enrollmentRepository.findByStudentRollNumber(rollNumber); // returns empty list if none
    }

    // ---------- Attendance ----------
    @GetMapping("/attendance")
    public List<Attendance> getMyAttendance(Principal principal) {
        String rollNumber = principal.getName();
        return attendanceRepository.findByStudentRollNumber(rollNumber);
    }

    // ---------- Qualification ----------
    @GetMapping("/qualification")
    public ResponseEntity<EducationalQualification> getMyQualification(Principal principal) {
        String rollNumber = principal.getName();
        return qualificationRepository.findById(rollNumber)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build()); // 404 if not found
    }

    // ---------- Certificates ----------
    @GetMapping("/certificates")
    public List<Certificate> getMyCertificates(Principal principal) {
        String rollNumber = principal.getName();
        return certificateRepository.findByStudentRollNumber(rollNumber); // empty list if none
    }

    @PostMapping("/certificate")
    public ResponseEntity<?> uploadCertificate(
            @RequestParam("type") String certificateTypeStr,
            @RequestParam("file") MultipartFile file,
            Principal principal) throws IOException {

        String rollNumber = principal.getName();
        CertificateType type;
        try {
            type = CertificateType.valueOf(certificateTypeStr.toUpperCase());
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body("Invalid certificate type");
        }

        // Remove existing certificate of the same type
        List<Certificate> existing = certificateRepository.findByStudentRollNumberAndCertificateType(rollNumber, type);
        certificateRepository.deleteAll(existing);

        Student student = studentRepository.findById(rollNumber)
                .orElseThrow(() -> new RuntimeException("Student not found"));

        Certificate certificate = new Certificate();
        certificate.setStudent(student);
        certificate.setCertificateType(type);
        certificate.setFileName(file.getOriginalFilename());
        certificate.setContentType(file.getContentType());
        certificate.setFileData(file.getBytes());
        certificate.setUploadDate(LocalDateTime.now());
        certificate.setVerified(false);

        certificateRepository.save(certificate);
        return ResponseEntity.ok("Certificate uploaded");
    }

    // ---------- Photo ----------
    @PostMapping("/photo")
    public ResponseEntity<?> uploadPhoto(@RequestParam("file") MultipartFile file, Principal principal) throws IOException {
        String rollNumber = principal.getName();
        StudentPhoto photo = new StudentPhoto();
        photo.setStudentRollNumber(rollNumber);
        photo.setImageData(file.getBytes());
        photo.setContentType(file.getContentType());
        photoRepository.save(photo);
        return ResponseEntity.ok("Photo uploaded");
    }

    @GetMapping("/photo")
    public ResponseEntity<byte[]> getPhoto(Principal principal) {
        String rollNumber = principal.getName();
        return photoRepository.findById(rollNumber)
                .map(photo -> ResponseEntity.ok()
                        .header("Content-Type", photo.getContentType())
                        .body(photo.getImageData()))
                .orElse(ResponseEntity.notFound().build()); // 404 if no photo
    }
}