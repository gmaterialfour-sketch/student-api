package com.university.student_api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.university.student_api.entity.Student;
import com.university.student_api.repository.EnrollmentRepository;
import com.university.student_api.repository.StudentRepository;

@RestController
@RequestMapping("/api/admin")
public class AdminController {

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private EnrollmentRepository enrollmentRepository;

    @GetMapping("/students")
    public Page<Student> listStudents(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(required = false) String department) {
        Pageable pageable = PageRequest.of(page, size);
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
    public ResponseEntity<Student> updateStudent(
            @PathVariable String rollNumber,
            @RequestBody Student updatedStudent) {
        return studentRepository.findById(rollNumber)
                .map(existing -> {
                    existing.setName(updatedStudent.getName());
                    existing.setFullName(updatedStudent.getFullName());
                    existing.setEmail(updatedStudent.getEmail());
                    existing.setAddress(updatedStudent.getAddress());
                    existing.setDepartment(updatedStudent.getDepartment());
                    existing.setSelectedCourse(updatedStudent.getSelectedCourse());
                    existing.setAcademicYear(updatedStudent.getAcademicYear());
                    studentRepository.save(existing);
                    return ResponseEntity.ok(existing);
                })
                .orElse(ResponseEntity.notFound().build());
    }

 @DeleteMapping("/students/{rollNumber}")
public ResponseEntity<Void> deleteStudent(@PathVariable String rollNumber) {
    Student student = studentRepository.findById(rollNumber).orElse(null);
    if (student == null) {
        return ResponseEntity.notFound().build();
    }
    // Prevent deletion of admin users
    if ("ROLE_ADMIN".equals(student.getRole())) {
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(null);
    }
    // Delete enrollments then student
    enrollmentRepository.findByIdStudentRollNumber(rollNumber)
            .forEach(enrollment -> enrollmentRepository.delete(enrollment));
    studentRepository.deleteById(rollNumber);
    return ResponseEntity.noContent().build();
}}