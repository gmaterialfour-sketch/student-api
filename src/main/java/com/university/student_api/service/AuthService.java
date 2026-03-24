package com.university.student_api.service;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.university.student_api.dto.AuthResponse;
import com.university.student_api.dto.LoginRequest;
import com.university.student_api.dto.RegisterRequest;
import com.university.student_api.entity.Course;
import com.university.student_api.entity.Department;
import com.university.student_api.entity.Enrollment;
import com.university.student_api.entity.EnrollmentId;
import com.university.student_api.entity.RollNumberSequence;
import com.university.student_api.entity.Student;
import com.university.student_api.repository.CourseRepository;
import com.university.student_api.repository.DepartmentRepository;
import com.university.student_api.repository.EnrollmentRepository;
import com.university.student_api.repository.RollNumberSequenceRepository;
import com.university.student_api.repository.StudentRepository;
import com.university.student_api.security.JwtUtil;

@Service
public class AuthService {

    @Autowired private StudentRepository studentRepository;
    @Autowired private DepartmentRepository departmentRepository;
    @Autowired private CourseRepository courseRepository;
    @Autowired private EnrollmentRepository enrollmentRepository;
    @Autowired private RollNumberSequenceRepository rollNumberSequenceRepository;
    @Autowired private PasswordEncoder passwordEncoder;
    @Autowired private JwtUtil jwtUtil;
    @Autowired private AuthenticationManager authManager;

    @Transactional
    public String register(RegisterRequest dto) {
        // Check duplicate Aadhaar
        if (studentRepository.existsByAadhaarNumber(dto.getAadhaarNumber())) {
            throw new RuntimeException("Aadhaar number already registered");
        }

        // Find department
        Department department = departmentRepository.findById(dto.getDepartmentCode())
                .orElseThrow(() -> new RuntimeException("Department not found"));

        // Fetch all courses the student selected
        List<Course> courses = courseRepository.findAllById(dto.getSelectedCourses());
        if (courses.size() != dto.getSelectedCourses().size()) {
            throw new RuntimeException("One or more selected courses do not exist");
        }

        // Generate roll number
        RollNumberSequence seq = rollNumberSequenceRepository.findSequenceForUpdate()
                .orElse(new RollNumberSequence(1L, 1000L));
        long nextNumber = seq.getCurrentValue() + 1;
        seq.setCurrentValue(nextNumber);
        rollNumberSequenceRepository.save(seq);
        String rollNumber = dto.getDepartmentCode() + String.format("%04d", nextNumber);

        // Create student
        Student s = new Student();
        s.setRollNumber(rollNumber);
        s.setAadhaarNumber(dto.getAadhaarNumber());
        s.setName(dto.getName());
        s.setFullName(dto.getFullName());
        s.setAddress(dto.getAddress());
        s.setGender(dto.getGender());
        s.setDepartment(department);
        s.setSelectedCourse(courses.isEmpty() ? null : courses.get(0).getCode()); // keep first as selected for compatibility
        s.setEmail(dto.getEmail());
        s.setAcademicYear(dto.getAcademicYear());
        s.setPassword(passwordEncoder.encode(dto.getPassword()));
        s.setRole("ROLE_STUDENT");

        studentRepository.save(s);

        // Create enrollments for each selected course
        for (Course course : courses) {
            Enrollment enrollment = new Enrollment();
            EnrollmentId id = new EnrollmentId(rollNumber, course.getCode());
            enrollment.setId(id);
            enrollment.setStudent(s);
            enrollment.setCourse(course);
            enrollment.setEnrollmentDate(LocalDateTime.now());
            enrollmentRepository.save(enrollment);
        }

        return "Registered successfully! Your roll number: " + rollNumber;
    }

    // ... login method unchanged
    public AuthResponse login(LoginRequest dto) {
        Student student = studentRepository.findById(dto.getRollNumber())
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (student.getFailedLoginAttempts() >= 3 && student.getLastFailedLoginTime() != null) {
            long minutes = ChronoUnit.MINUTES.between(student.getLastFailedLoginTime(), LocalDateTime.now());
            if (minutes < 5) {
                throw new RuntimeException("Account locked. Try again after " + (5 - minutes) + " minutes.");
            }
            student.setFailedLoginAttempts(0);
            student.setLastFailedLoginTime(null);
            studentRepository.save(student);
        }

        try {
            authManager.authenticate(
                    new UsernamePasswordAuthenticationToken(dto.getRollNumber(), dto.getPassword())
            );

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