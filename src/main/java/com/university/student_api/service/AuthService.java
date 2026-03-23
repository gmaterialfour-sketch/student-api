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

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

@Service
public class AuthService {

    @Autowired private StudentRepository studentRepository;
    @Autowired private DepartmentRepository departmentRepository;
    @Autowired private CourseRepository courseRepository;
    @Autowired private EnrollmentRepository enrollmentRepository;
    @Autowired private PasswordEncoder passwordEncoder;
    @Autowired private JwtUtil jwtUtil;
    @Autowired private AuthenticationManager authManager;

    public String register(RegisterRequest dto) {
        // Check duplicate roll number
        if (studentRepository.existsById(dto.getRollNumber())) {
            throw new RuntimeException("Roll number already registered");
        }
        // Check duplicate Aadhaar
        if (studentRepository.existsByAadhaarNumber(dto.getAadhaarNumber())) {
            throw new RuntimeException("Aadhaar number already registered");
        }

        // Find department
        Department department = departmentRepository.findById(dto.getDepartmentCode())
                .orElseThrow(() -> new RuntimeException("Department not found"));

        // Find course (optional, for enrollment)
        Course course = courseRepository.findById(dto.getSelectedCourse())
                .orElseThrow(() -> new RuntimeException("Course not found"));

        // Create student
        Student s = new Student();
        s.setRollNumber(dto.getRollNumber());
        s.setAadhaarNumber(dto.getAadhaarNumber());
        s.setName(dto.getName());
        s.setFullName(dto.getFullName());
        s.setAddress(dto.getAddress());
        s.setDepartment(department);
        s.setSelectedCourse(dto.getSelectedCourse());
        s.setEmail(dto.getEmail());
        s.setAcademicYear(dto.getAcademicYear());
        s.setPassword(passwordEncoder.encode(dto.getPassword()));
        s.setRole("ROLE_STUDENT");

        studentRepository.save(s);

        // Create enrollment
        Enrollment enrollment = new Enrollment();
        EnrollmentId enrollmentId = new EnrollmentId(dto.getRollNumber(), dto.getSelectedCourse());
        enrollment.setId(enrollmentId);
        enrollment.setStudent(s);
        enrollment.setCourse(course);
        enrollment.setEnrollmentDate(LocalDateTime.now());
        enrollmentRepository.save(enrollment);

        return "Registered successfully! Your roll number: " + dto.getRollNumber();
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