package com.university.student_api.dto;

import org.springframework.web.multipart.MultipartFile;

public class PaymentProofUploadRequest {
    private MultipartFile file;
    // getter/setter

    public MultipartFile getFile() {
        return file;
    }

    public void setFile(MultipartFile file) {
        this.file = file;
    }
}