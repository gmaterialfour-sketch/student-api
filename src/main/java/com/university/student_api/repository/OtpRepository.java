package com.university.student_api.repository;

import com.university.student_api.entity.Otp;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface OtpRepository extends JpaRepository<Otp, Long> {
    void deleteByEmail(String email);
    Optional<Otp> findByEmailAndOtpAndVerifiedFalse(String email, String otp);
}