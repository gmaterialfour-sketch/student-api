package com.university.student_api.controller;

import com.university.student_api.entity.Student;
import com.university.student_api.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RestController
@RequestMapping("/api/student")
public class StudentController {

    @Autowired
    private StudentService studentService;

    @GetMapping("/me")
    public ResponseEntity<?> getCurrentStudent(Principal principal) {
        String rollNumber = principal.getName();
        Student student = studentService.findByRollNumber(rollNumber);
        if (student == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(student);
    }
}