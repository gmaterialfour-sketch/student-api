package com.university.student_api.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.university.student_api.entity.Department;

public interface DepartmentRepository extends JpaRepository<Department, String> {
}