package com.university.student_api.controller;

import com.university.student_api.entity.*;
import com.university.student_api.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.*;

@RestController
@RequestMapping("/api/admin")
@PreAuthorize("hasRole('ADMIN')")
public class AdminController {

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private CourseRepository courseRepository;

    @Autowired
    private DepartmentRepository departmentRepository;

    @Autowired
    private EnrollmentRepository enrollmentRepository;

    @Autowired
    private CertificateRepository certificateRepository;

    @Autowired
    private EducationalQualificationRepository qualificationRepository;

    @Autowired
    private AttendanceRepository attendanceRepository;

    // -------------------- Statistics --------------------
    @GetMapping("/stats")
    public Map<String, Long> getStats() {
        Map<String, Long> stats = new HashMap<>();
        stats.put("totalStudents", studentRepository.count());
        stats.put("totalCourses", courseRepository.count());
        stats.put("totalDepartments", departmentRepository.count());
        stats.put("totalEnrollments", enrollmentRepository.count());
        return stats;
    }

    // -------------------- Students --------------------
    @GetMapping("/students")
    public Page<Student> listStudents(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(required = false) String aadhaar,
            @RequestParam(required = false) String department) {

        Pageable pageable = PageRequest.of(page, size, Sort.by("rollNumber").ascending());

        if (aadhaar != null && !aadhaar.isEmpty()) {
            return studentRepository.findByAadhaarNumber(aadhaar)
                    .map(s -> new PageImpl<>(Collections.singletonList(s), pageable, 1))
                    .orElse(new PageImpl<>(Collections.emptyList(), pageable, 0));
        }

        if (department != null && !department.isEmpty()) {
            return studentRepository.findByDepartment_Code(department, pageable);
        }

        return studentRepository.findAll(pageable);
    }

    @GetMapping("/students/{rollNumber}")
    public ResponseEntity<Student> getStudent(@PathVariable String rollNumber) {
        return studentRepository.findById(rollNumber)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/students/{rollNumber}")
    public ResponseEntity<?> updateStudent(@PathVariable String rollNumber, @RequestBody Student updated) {
        return studentRepository.findById(rollNumber)
                .map(student -> {
                    student.setName(updated.getName());
                    student.setFullName(updated.getFullName());
                    student.setEmail(updated.getEmail());
                    student.setAddress(updated.getAddress());
                    student.setAcademicYear(updated.getAcademicYear());
                    student.setGender(updated.getGender());
                    if (updated.getDepartment() != null) {
                        Department dept = departmentRepository.findById(updated.getDepartment().getCode())
                                .orElseThrow(() -> new RuntimeException("Department not found"));
                        student.setDepartment(dept);
                    }
                    studentRepository.save(student);
                    return ResponseEntity.ok("Student updated");
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/students/{rollNumber}")
    public ResponseEntity<?> deleteStudent(@PathVariable String rollNumber) {
        Student student = studentRepository.findById(rollNumber)
                .orElseThrow(() -> new RuntimeException("Student not found"));
        if (student.getRole() == Role.ADMIN) {
            return ResponseEntity.badRequest().body("Cannot delete admin user");
        }
        studentRepository.delete(student);
        return ResponseEntity.ok("Student deleted");
    }

    // -------------------- Courses --------------------
    @GetMapping("/courses")
    public List<Course> getAllCourses() {
        return courseRepository.findAll();
    }

    // -------------------- Enrollments --------------------
    @GetMapping("/enrollments")
    public List<Enrollment> getAllEnrollments() {
        return enrollmentRepository.findAll();
    }

    @PutMapping("/enrollments/{studentRoll}/{courseCode}/grade")
    public ResponseEntity<?> updateGrade(@PathVariable String studentRoll,
                                         @PathVariable String courseCode,
                                         @RequestBody Map<String, String> body) {
        EnrollmentId id = new EnrollmentId(courseCode, studentRoll);
        Enrollment enrollment = enrollmentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Enrollment not found"));
        enrollment.setGrade(body.get("grade"));
        enrollmentRepository.save(enrollment);
        return ResponseEntity.ok("Grade updated");
    }

    // -------------------- Attendance --------------------
    @PostMapping("/attendance")
    public ResponseEntity<?> markAttendance(@RequestBody Attendance attendance) {
        attendance.setDate(LocalDateTime.now().toLocalDate());
        attendanceRepository.save(attendance);
        return ResponseEntity.ok("Attendance marked");
    }

    // -------------------- Qualifications --------------------
    @GetMapping("/pending-qualifications")
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
        }).toList();
    }

    @PutMapping("/students/{rollNumber}/qualification/verify")
    public ResponseEntity<?> verifyQualification(@PathVariable String rollNumber) {
        EducationalQualification q = qualificationRepository.findById(rollNumber)
                .orElseThrow(() -> new RuntimeException("Qualification not found"));
        q.setVerified(true);
        qualificationRepository.save(q);
        return ResponseEntity.ok("Qualification verified");
    }

    // -------------------- Certificates --------------------
    @GetMapping("/students/{rollNumber}/certificates")
    public List<Certificate> getStudentCertificates(@PathVariable String rollNumber) {
        return certificateRepository.findByStudentRollNumber(rollNumber);
    }

    @GetMapping("/certificates/pending")
    public List<Certificate> getPendingCertificates() {
        return certificateRepository.findByVerifiedFalse();
    }

    @PutMapping("/certificates/{id}/verify")
    public ResponseEntity<?> verifyCertificate(@PathVariable Long id) {
        Certificate cert = certificateRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Certificate not found"));
        cert.setVerified(true);
        cert.setVerificationDate(LocalDateTime.now());
        cert.setVerifiedBy(SecurityContextHolder.getContext().getAuthentication().getName());
        certificateRepository.save(cert);
        return ResponseEntity.ok("Certificate verified");
    }

    @GetMapping("/certificates/{id}/download")
    public ResponseEntity<byte[]> downloadCertificate(@PathVariable Long id) {
        Certificate cert = certificateRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Certificate not found"));
        return ResponseEntity.ok()
                .header("Content-Disposition", "attachment; filename=\"" + cert.getFileName() + "\"")
                .header("Content-Type", cert.getContentType())
                .body(cert.getFileData());
    }
}