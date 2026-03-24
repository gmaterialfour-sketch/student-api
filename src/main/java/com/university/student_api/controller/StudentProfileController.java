package com.university.student_api.controller;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.university.student_api.dto.EducationalQualificationRequest;
import com.university.student_api.entity.Certificate;
import com.university.student_api.entity.EducationalQualification;
import com.university.student_api.entity.PaymentProof;
import com.university.student_api.entity.Student;
import com.university.student_api.entity.StudentPhoto;
import com.university.student_api.repository.CertificateRepository;
import com.university.student_api.repository.EducationalQualificationRepository;
import com.university.student_api.repository.PaymentProofRepository;
import com.university.student_api.repository.StudentPhotoRepository;
import com.university.student_api.repository.StudentRepository;

@RestController
@RequestMapping("/api")
public class StudentProfileController {

    @Autowired private StudentPhotoRepository photoRepository;
    @Autowired private EducationalQualificationRepository qualificationRepository;
    @Autowired private PaymentProofRepository paymentProofRepository;
    @Autowired private StudentRepository studentRepository;
    @Autowired private CertificateRepository certificateRepository;

    // ---------- PHOTO ----------
    @PostMapping("/student/photo")
    public ResponseEntity<?> uploadPhoto(@RequestParam("file") MultipartFile file) throws IOException {
        String rollNumber = SecurityContextHolder.getContext().getAuthentication().getName();
        StudentPhoto photo = new StudentPhoto();
        photo.setStudentRollNumber(rollNumber);
        photo.setImageData(file.getBytes());
        photo.setContentType(file.getContentType());
        photoRepository.save(photo);
        return ResponseEntity.ok("Photo uploaded");
    }

    @GetMapping("/student/photo")
    public ResponseEntity<byte[]> getPhoto() {
        String rollNumber = SecurityContextHolder.getContext().getAuthentication().getName();
        StudentPhoto photo = photoRepository.findById(rollNumber)
                .orElseThrow(() -> new RuntimeException("No photo found"));
        return ResponseEntity.ok()
                .header("Content-Type", photo.getContentType())
                .body(photo.getImageData());
    }

    @GetMapping("/admin/students/{rollNumber}/photo")
    public ResponseEntity<byte[]> getStudentPhoto(@PathVariable String rollNumber) {
        StudentPhoto photo = photoRepository.findById(rollNumber)
                .orElseThrow(() -> new RuntimeException("No photo found"));
        return ResponseEntity.ok()
                .header("Content-Type", photo.getContentType())
                .body(photo.getImageData());
    }

    // ---------- QUALIFICATION ----------
    @PostMapping("/student/qualification")
    public ResponseEntity<?> saveQualification(@RequestBody EducationalQualificationRequest req) {
        String rollNumber = SecurityContextHolder.getContext().getAuthentication().getName();
        EducationalQualification q = new EducationalQualification();
        q.setStudentRollNumber(rollNumber);
        q.setTenthBoard(req.getTenthBoard());
        q.setTenthYear(req.getTenthYear());
        q.setTenthPercentage(req.getTenthPercentage());
        q.setQualificationType(req.getQualificationType());

        if ("12TH".equals(req.getQualificationType())) {
            q.setTwelfthBoard(req.getTwelfthBoard());
            q.setTwelfthYear(req.getTwelfthYear());
            q.setTwelfthPercentage(req.getTwelfthPercentage());
        } else if ("DIPLOMA".equals(req.getQualificationType())) {
            q.setDiplomaBranch(req.getDiplomaBranch());
            q.setDiplomaCredits(req.getDiplomaCredits());
        } else if ("ITI".equals(req.getQualificationType())) {
            q.setItiTrade(req.getItiTrade());
            q.setItiYear(req.getItiYear());
            q.setItiPercentage(req.getItiPercentage());
        }
        q.setVerified(false);
        qualificationRepository.save(q);
        return ResponseEntity.ok("Qualifications saved");
    }

    @GetMapping("/student/qualification")
    public EducationalQualification getMyQualification() {
        String rollNumber = SecurityContextHolder.getContext().getAuthentication().getName();
        return qualificationRepository.findById(rollNumber)
                .orElseThrow(() -> new RuntimeException("Qualification not found"));
    }

    @GetMapping("/admin/students/{rollNumber}/qualification")
    public EducationalQualification getStudentQualification(@PathVariable String rollNumber) {
        return qualificationRepository.findById(rollNumber)
                .orElseThrow(() -> new RuntimeException("Qualification not found"));
    }

    @PutMapping("/admin/students/{rollNumber}/qualification")
    public ResponseEntity<?> updateQualification(@PathVariable String rollNumber, @RequestBody EducationalQualificationRequest req) {
        EducationalQualification q = qualificationRepository.findById(rollNumber)
                .orElseThrow(() -> new RuntimeException("Qualification not found"));
        q.setTenthBoard(req.getTenthBoard());
        q.setTenthYear(req.getTenthYear());
        q.setTenthPercentage(req.getTenthPercentage());
        q.setQualificationType(req.getQualificationType());
        if ("12TH".equals(req.getQualificationType())) {
            q.setTwelfthBoard(req.getTwelfthBoard());
            q.setTwelfthYear(req.getTwelfthYear());
            q.setTwelfthPercentage(req.getTwelfthPercentage());
        } else if ("DIPLOMA".equals(req.getQualificationType())) {
            q.setDiplomaBranch(req.getDiplomaBranch());
            q.setDiplomaCredits(req.getDiplomaCredits());
        } else if ("ITI".equals(req.getQualificationType())) {
            q.setItiTrade(req.getItiTrade());
            q.setItiYear(req.getItiYear());
            q.setItiPercentage(req.getItiPercentage());
        }
        qualificationRepository.save(q);
        return ResponseEntity.ok("Qualification updated");
    }

    @PutMapping("/admin/students/{rollNumber}/qualification/verify")
    public ResponseEntity<?> verifyQualification(@PathVariable String rollNumber) {
        EducationalQualification q = qualificationRepository.findById(rollNumber)
                .orElseThrow(() -> new RuntimeException("Qualification not found"));
        q.setVerified(true);
        qualificationRepository.save(q);
        return ResponseEntity.ok("Qualification verified");
    }

    @GetMapping("/admin/pending-qualifications")
    public List<Map<String, Object>> getPendingQualifications() {
        List<EducationalQualification> pending = qualificationRepository.findAll().stream()
                .filter(q -> !q.isVerified())
                .toList();
        return pending.stream().map(q -> {
            Map<String, Object> map = new HashMap<>();
            map.put("studentRollNumber", q.getStudentRollNumber());
            Student student = studentRepository.findById(q.getStudentRollNumber()).orElse(null);
            map.put("studentName", student != null ? student.getName() : "Unknown");
            map.put("qualificationType", q.getQualificationType());
            return map;
        }).collect(Collectors.toList());
    }

    // ---------- PAYMENT PROOF ----------
    @PostMapping("/student/payment")
    public ResponseEntity<?> uploadPaymentProof(@RequestParam("file") MultipartFile file) throws IOException {
        String rollNumber = SecurityContextHolder.getContext().getAuthentication().getName();
        PaymentProof proof = new PaymentProof();
        proof.setStudentRollNumber(rollNumber);
        proof.setPdfData(file.getBytes());
        proof.setFileName(file.getOriginalFilename());
        proof.setContentType(file.getContentType());
        proof.setUploadDate(LocalDateTime.now());
        proof.setApproved(false);
        paymentProofRepository.save(proof);
        return ResponseEntity.ok("Payment proof uploaded. Awaiting admin approval.");
    }

    @GetMapping("/student/payment")
    public PaymentProof getMyPaymentStatus() {
        String rollNumber = SecurityContextHolder.getContext().getAuthentication().getName();
        return paymentProofRepository.findById(rollNumber)
                .orElseThrow(() -> new RuntimeException("Payment proof not found"));
    }

    @GetMapping("/admin/payments/pending")
    public List<PaymentProof> getPendingPayments() {
        return paymentProofRepository.findAll().stream()
                .filter(p -> !p.isApproved())
                .toList();
    }

    @PutMapping("/admin/payments/{rollNumber}/approve")
    public ResponseEntity<?> approvePayment(@PathVariable String rollNumber) {
        PaymentProof proof = paymentProofRepository.findById(rollNumber)
                .orElseThrow(() -> new RuntimeException("Payment proof not found"));
        proof.setApproved(true);
        proof.setApprovalDate(LocalDateTime.now());
        proof.setApprovedBy(SecurityContextHolder.getContext().getAuthentication().getName());
        paymentProofRepository.save(proof);
        return ResponseEntity.ok("Payment approved");
    }

    // ---------- CERTIFICATES ----------
    @PostMapping("/student/certificate")
    public ResponseEntity<?> uploadCertificate(
            @RequestParam("type") String certificateType,
            @RequestParam("file") MultipartFile file) throws IOException {
        String rollNumber = SecurityContextHolder.getContext().getAuthentication().getName();

        // Remove existing certificate of the same type (optional)
        List<Certificate> existing = certificateRepository.findByStudentRollNumberAndCertificateType(rollNumber, certificateType);
        certificateRepository.deleteAll(existing);

        Certificate cert = new Certificate();
        cert.setStudentRollNumber(rollNumber);
        cert.setCertificateType(certificateType);
        cert.setFileData(file.getBytes());
        cert.setFileName(file.getOriginalFilename());
        cert.setContentType(file.getContentType());
        cert.setUploadDate(LocalDateTime.now());
        cert.setVerified(false);
        certificateRepository.save(cert);
        return ResponseEntity.ok("Certificate uploaded for " + certificateType);
    }

    @GetMapping("/student/certificates")
    public List<Certificate> getMyCertificates() {
        String rollNumber = SecurityContextHolder.getContext().getAuthentication().getName();
        return certificateRepository.findByStudentRollNumber(rollNumber);
    }

    @GetMapping("/admin/certificates/pending")
    public List<Certificate> getPendingCertificates() {
        return certificateRepository.findByVerifiedFalse();
    }

    @GetMapping("/admin/certificates/{id}")
    public ResponseEntity<byte[]> downloadCertificate(@PathVariable Long id) {
        Certificate cert = certificateRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Certificate not found"));
        return ResponseEntity.ok()
                .header("Content-Disposition", "attachment; filename=\"" + cert.getFileName() + "\"")
                .header("Content-Type", cert.getContentType())
                .body(cert.getFileData());
    }

    @PutMapping("/admin/certificates/{id}/verify")
    public ResponseEntity<?> verifyCertificate(@PathVariable Long id) {
        Certificate cert = certificateRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Certificate not found"));
        cert.setVerified(true);
        cert.setVerificationDate(LocalDateTime.now());
        cert.setVerifiedBy(SecurityContextHolder.getContext().getAuthentication().getName());
        certificateRepository.save(cert);
        return ResponseEntity.ok("Certificate verified");
    }
}