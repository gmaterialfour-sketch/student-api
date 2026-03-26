package com.university.student_api.repository;

import com.university.student_api.entity.EducationalQualification;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface EducationalQualificationRepository extends JpaRepository<EducationalQualification, String> {
    Optional<EducationalQualification> findByStudentRollNumber(String rollNumber);
}