package com.university.student_api.service;

import com.university.student_api.entity.Otp;
import com.university.student_api.repository.OtpRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.Random;

@Service
public class OtpService {
    @Autowired
    private OtpRepository otpRepository;
    @Autowired
    private EmailService emailService;

    @Transactional
    public String sendOtp(String email) {
        String otp = String.format("%06d", new Random().nextInt(999999));
        otpRepository.deleteByEmail(email);
        Otp otpEntity = new Otp();
        otpEntity.setEmail(email);
        otpEntity.setOtp(otp);
        otpEntity.setExpiryTime(LocalDateTime.now().plusMinutes(5));
        otpEntity.setVerified(false);
        otpRepository.save(otpEntity);
        emailService.sendOtp(email, otp);
        return "OTP sent to " + email;
    }

    public boolean verifyOtp(String email, String otp) {
        Optional<Otp> otpEntity = otpRepository.findByEmailAndOtpAndVerifiedFalse(email, otp);
        if (otpEntity.isPresent() && otpEntity.get().getExpiryTime().isAfter(LocalDateTime.now())) {
            Otp entity = otpEntity.get();
            entity.setVerified(true);
            otpRepository.save(entity);
            return true;
        }
        return false;
    }
}