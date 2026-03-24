package com.university.student_api.dto;

import org.springframework.web.multipart.MultipartFile;

public class CertificateUpload {
    private String type; // "10TH", "12TH", "DIPLOMA", "ITI"
    private MultipartFile file;

    public MultipartFile getFile() {
        return file;
    }

    public void setFile(MultipartFile file) {
        this.file = file;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}