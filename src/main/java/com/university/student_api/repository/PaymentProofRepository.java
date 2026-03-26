package com.university.student_api.repository;

import com.university.student_api.entity.PaymentProof;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaymentProofRepository extends JpaRepository<PaymentProof, String> {}