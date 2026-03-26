package com.university.student_api.repository;

import com.university.student_api.entity.Course;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface CourseRepository extends JpaRepository<Course, String> {
    // This method is correct: Department is a field, code is a field inside Department
    List<Course> findByDepartment_Code(String departmentCode);
}