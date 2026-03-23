package com.university.student_api.controller;

import com.university.student_api.entity.Student;
import com.university.student_api.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/student")
public class StudentController {

    @Autowired
    private StudentRepository studentRepository;

    @GetMapping("/me")
    public ResponseEntity<Student> getMyProfile() {
        String rollNumber = SecurityContextHolder.getContext().getAuthentication().getName();
        return studentRepository.findById(rollNumber)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/me")
    public ResponseEntity<Student> updateMyProfile(@RequestBody Student updatedStudent) {
        String rollNumber = SecurityContextHolder.getContext().getAuthentication().getName();
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
}