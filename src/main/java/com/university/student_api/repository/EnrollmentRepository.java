package com.university.student_api.repository;

import com.university.student_api.entity.Enrollment;
import com.university.student_api.entity.EnrollmentId;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface EnrollmentRepository extends JpaRepository<Enrollment, EnrollmentId> {
    // Finds all enrollments for a given student roll number
    List<Enrollment> findByStudentRollNumber(String rollNumber);
}