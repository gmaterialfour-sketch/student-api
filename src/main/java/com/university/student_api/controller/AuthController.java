package com.university.student_api.controller;

import com.university.student_api.dto.AuthResponse;
import com.university.student_api.dto.LoginRequest;
import com.university.student_api.dto.RegisterRequest;
import com.university.student_api.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private AuthService authService;

    @PostMapping(value = "/register", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<String> register(
            @RequestPart("student") RegisterRequest req,
            @RequestPart(value = "photo", required = false) MultipartFile photo,
            @RequestPart(value = "cert10", required = false) MultipartFile cert10,
            @RequestPart(value = "cert12", required = false) MultipartFile cert12,
            @RequestPart(value = "certDiploma", required = false) MultipartFile certDiploma,
            @RequestPart(value = "certIti", required = false) MultipartFile certIti) throws IOException {
        return ResponseEntity.ok(authService.register(req, photo, cert10, cert12, certDiploma, certIti));
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody LoginRequest req) {
        return ResponseEntity.ok(authService.login(req));
    }
}