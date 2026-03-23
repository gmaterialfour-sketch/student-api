package com.university.student_api.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.university.student_api.entity.Course;

@Repository
public interface CourseRepository extends JpaRepository<Course, String> {
    List<Course> findByDepartment_Code(String departmentCode);
}