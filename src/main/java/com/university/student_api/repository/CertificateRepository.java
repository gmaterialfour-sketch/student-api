package com.university.student_api.repository;

import com.university.student_api.entity.Certificate;
import com.university.student_api.entity.CertificateType;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;

public interface CertificateRepository extends JpaRepository<Certificate, Long> {
    List<Certificate> findByStudentRollNumber(String rollNumber);
    List<Certificate> findByStudentRollNumberAndCertificateType(String rollNumber, CertificateType type);
    List<Certificate> findByVerifiedFalse();
    Optional<Certificate> findByIdAndStudentRollNumber(Long id, String rollNumber);
}