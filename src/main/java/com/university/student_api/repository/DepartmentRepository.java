package com.university.student_api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.university.student_api.entity.Department;

@Repository
public interface DepartmentRepository extends JpaRepository<Department, String> {
}