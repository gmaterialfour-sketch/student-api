package com.university.student_api.repository;

import com.university.student_api.entity.Otp;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface OtpRepository extends JpaRepository<Otp, Long> {
    Optional<Otp> findByEmailAndOtpAndVerifiedFalse(String email, String otp);
    void deleteByEmail(String email);
}