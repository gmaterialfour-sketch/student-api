package com.university.student_api.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.university.student_api.entity.Enrollment;
import com.university.student_api.entity.EnrollmentId;

@Repository
public interface EnrollmentRepository extends JpaRepository<Enrollment, EnrollmentId> {
    List<Enrollment> findByIdStudentRollNumber(String studentRollNumber);
    List<Enrollment> findByIdCourseCode(String courseCode);
}