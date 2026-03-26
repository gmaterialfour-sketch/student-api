package com.university.student_api.repository;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.university.student_api.entity.Student;

public interface StudentRepository extends JpaRepository<Student, String> {
    // ✅ Correct method name
    Optional<Student> findByAadhaarNumber(String aadhaarNumber);

    Optional<Student> findByEmail(String email);

    // Method to find students by department code (using underscore for nested property)
    Page<Student> findByDepartment_Code(String departmentCode, Pageable pageable);
}