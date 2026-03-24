package com.university.student_api.repository;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.university.student_api.entity.Student;

@Repository
public interface StudentRepository extends JpaRepository<Student, String> {
    boolean existsByAadhaarNumber(String aadhaarNumber);
    Optional<Student> findByAadhaarNumber(String aadhaarNumber);
    Page<Student> findByDepartment_Code(String departmentCode, Pageable pageable);
}