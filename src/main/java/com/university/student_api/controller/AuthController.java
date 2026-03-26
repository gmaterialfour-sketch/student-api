package com.university.student_api.controller;

import com.university.student_api.service.AuthService;
import com.university.student_api.service.OtpService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private AuthService authService;

    @Autowired
    private OtpService otpService;

    @PostMapping("/register")
    public ResponseEntity<?> register(
            @RequestPart("student") String studentJson,
            @RequestPart(value = "photo", required = false) MultipartFile photo,
            @RequestPart(value = "cert10", required = false) MultipartFile cert10,
            @RequestPart(value = "cert12", required = false) MultipartFile cert12,
            @RequestPart(value = "certIti", required = false) MultipartFile certIti,
            @RequestPart(value = "certDiploma", required = false) MultipartFile certDiploma) {
        try {
            String result = authService.register(studentJson, photo, cert10, cert12, certIti, certDiploma);
            return ResponseEntity.ok(result);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Map<String, String> credentials) {
        try {
            Map<String, String> response = authService.login(credentials.get("rollNumber"), credentials.get("password"));
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/otp/send")
    public ResponseEntity<?> sendOtp(@RequestBody Map<String, String> request) {
        try {
            String result = otpService.sendOtp(request.get("email"));
            return ResponseEntity.ok(Map.of("message", result));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/otp/verify")
    public ResponseEntity<?> verifyOtp(@RequestBody Map<String, String> request) {
        boolean verified = otpService.verifyOtp(request.get("email"), request.get("otp"));
        if (verified)
            return ResponseEntity.ok(Map.of("message", "OTP verified successfully"));
        else
            return ResponseEntity.badRequest().body("Invalid or expired OTP");
    }
}