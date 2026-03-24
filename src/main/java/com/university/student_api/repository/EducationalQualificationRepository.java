package com.university.student_api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.university.student_api.entity.EducationalQualification;

@Repository
public interface EducationalQualificationRepository extends JpaRepository<EducationalQualification, String> {
}