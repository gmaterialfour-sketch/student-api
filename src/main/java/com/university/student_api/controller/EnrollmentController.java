package com.university.student_api.controller;

import com.university.student_api.entity.Enrollment;
import com.university.student_api.repository.EnrollmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/api/enrollments")
public class EnrollmentController {

    @Autowired
    private EnrollmentRepository enrollmentRepository;

    @GetMapping("/my-courses")
    public List<Enrollment> getMyCourses(Principal principal) {
        String rollNumber = principal.getName();
        return enrollmentRepository.findByStudentRollNumber(rollNumber);
    }
}