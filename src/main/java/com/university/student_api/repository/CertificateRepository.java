package com.university.student_api.repository;

import com.university.student_api.entity.Certificate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CertificateRepository extends JpaRepository<Certificate, Long> {
    List<Certificate> findByStudentRollNumber(String studentRollNumber);
    List<Certificate> findByStudentRollNumberAndCertificateType(String studentRollNumber, String certificateType);
    List<Certificate> findByVerifiedFalse();
    Optional<Certificate> findByIdAndStudentRollNumber(Long id, String studentRollNumber);
}