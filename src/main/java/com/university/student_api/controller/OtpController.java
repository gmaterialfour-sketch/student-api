package com.university.student_api.controller;

import com.university.student_api.entity.Otp;
import com.university.student_api.repository.OtpRepository;
import com.university.student_api.service.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

@RestController
@RequestMapping("/api/otp")
public class OtpController {

    @Autowired
    private OtpRepository otpRepository;

    @Autowired
    private EmailService emailService;

    @PostMapping("/send")
    public ResponseEntity<?> sendOtp(@RequestBody Map<String, String> request) {
        String email = request.get("email");
        if (email == null || email.isEmpty()) {
            return ResponseEntity.badRequest().body("Email required");
        }

        // Generate 6-digit OTP
        String otp = String.format("%06d", new Random().nextInt(999999));

        // Remove old OTP for this email
        otpRepository.deleteByEmail(email);

        // Save new OTP
        Otp otpEntity = new Otp();
        otpEntity.setEmail(email);
        otpEntity.setOtp(otp);
        otpEntity.setExpiryTime(LocalDateTime.now().plusMinutes(5));
        otpEntity.setVerified(false);
        otpRepository.save(otpEntity);

        // Send email
        emailService.sendOtp(email, otp);

        Map<String, String> response = new HashMap<>();
        response.put("message", "OTP sent to " + email);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/verify")
    public ResponseEntity<?> verifyOtp(@RequestBody Map<String, String> request) {
        String email = request.get("email");
        String otp = request.get("otp");

        Otp otpEntity = otpRepository.findByEmailAndOtpAndVerifiedFalse(email, otp)
                .orElse(null);

        if (otpEntity == null) {
            return ResponseEntity.badRequest().body("Invalid OTP or email");
        }

        if (otpEntity.getExpiryTime().isBefore(LocalDateTime.now())) {
            return ResponseEntity.badRequest().body("OTP expired");
        }

        otpEntity.setVerified(true);
        otpRepository.save(otpEntity);
        return ResponseEntity.ok(Map.of("message", "OTP verified successfully"));
    }
}