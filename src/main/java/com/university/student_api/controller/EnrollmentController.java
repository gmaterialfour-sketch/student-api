package com.university.student_api.controller;

import com.university.student_api.dto.EnrollmentRequest;
import com.university.student_api.entity.Course;
import com.university.student_api.entity.Enrollment;
import com.university.student_api.entity.EnrollmentId;
import com.university.student_api.entity.Student;
import com.university.student_api.repository.CourseRepository;
import com.university.student_api.repository.EnrollmentRepository;
import com.university.student_api.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api")
public class EnrollmentController {

    @Autowired
    private EnrollmentRepository enrollmentRepository;

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private CourseRepository courseRepository;

    @PostMapping("/student/enroll")
    public ResponseEntity<?> enrollStudent(@RequestBody EnrollmentRequest req) {
        String loggedInRoll = SecurityContextHolder.getContext().getAuthentication().getName();
        if (!loggedInRoll.equals(req.getStudentRollNumber())) {
            return ResponseEntity.status(403).body("You can only enroll yourself.");
        }

        Student student = studentRepository.findById(req.getStudentRollNumber())
                .orElseThrow(() -> new RuntimeException("Student not found"));
        Course course = courseRepository.findById(req.getCourseCode())
                .orElseThrow(() -> new RuntimeException("Course not found"));

        EnrollmentId id = new EnrollmentId(req.getStudentRollNumber(), req.getCourseCode());
        if (enrollmentRepository.existsById(id)) {
            return ResponseEntity.badRequest().body("Already enrolled in this course");
        }

        Enrollment enrollment = new Enrollment();
        enrollment.setId(id);
        enrollment.setStudent(student);
        enrollment.setCourse(course);
        enrollment.setEnrollmentDate(LocalDateTime.now());

        enrollmentRepository.save(enrollment);
        return ResponseEntity.ok("Enrolled successfully");
    }

    @DeleteMapping("/student/enroll/{courseCode}")
    public ResponseEntity<?> dropCourse(@PathVariable String courseCode) {
        String loggedInRoll = SecurityContextHolder.getContext().getAuthentication().getName();
        EnrollmentId id = new EnrollmentId(loggedInRoll, courseCode);
        if (enrollmentRepository.existsById(id)) {
            enrollmentRepository.deleteById(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }

    @GetMapping("/student/enrollments")
    public List<Enrollment> getMyEnrollments() {
        String loggedInRoll = SecurityContextHolder.getContext().getAuthentication().getName();
        return enrollmentRepository.findByIdStudentRollNumber(loggedInRoll);
    }

    @GetMapping("/admin/students/{rollNumber}/enrollments")
    public List<Enrollment> getStudentEnrollments(@PathVariable String rollNumber) {
        return enrollmentRepository.findByIdStudentRollNumber(rollNumber);
    }

    @GetMapping("/admin/courses/{code}/students")
    public List<Enrollment> getCourseEnrollments(@PathVariable String code) {
        return enrollmentRepository.findByIdCourseCode(code);
    }

    @GetMapping("/admin/enrollments")
    public List<Enrollment> getAllEnrollments() {
        return enrollmentRepository.findAll();
    }
}