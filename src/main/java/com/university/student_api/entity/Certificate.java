package com.university.student_api.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "certificates")
public class Certificate {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String studentRollNumber;

    @Column(nullable = false)
    private String certificateType;

    @Lob
    private byte[] fileData;

    private String fileName;
    private String contentType;
    private LocalDateTime uploadDate;
    private boolean verified = false;
    private LocalDateTime verificationDate;
    private String verifiedBy;

    // Constructors
    public Certificate() {}

    public Certificate(Long id, String studentRollNumber, String certificateType, byte[] fileData,
                       String fileName, String contentType, LocalDateTime uploadDate,
                       boolean verified, LocalDateTime verificationDate, String verifiedBy) {
        this.id = id;
        this.studentRollNumber = studentRollNumber;
        this.certificateType = certificateType;
        this.fileData = fileData;
        this.fileName = fileName;
        this.contentType = contentType;
        this.uploadDate = uploadDate;
        this.verified = verified;
        this.verificationDate = verificationDate;
        this.verifiedBy = verifiedBy;
    }

    // Getters and setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getStudentRollNumber() { return studentRollNumber; }
    public void setStudentRollNumber(String studentRollNumber) { this.studentRollNumber = studentRollNumber; }

    public String getCertificateType() { return certificateType; }
    public void setCertificateType(String certificateType) { this.certificateType = certificateType; }

    public byte[] getFileData() { return fileData; }
    public void setFileData(byte[] fileData) { this.fileData = fileData; }

    public String getFileName() { return fileName; }
    public void setFileName(String fileName) { this.fileName = fileName; }

    public String getContentType() { return contentType; }
    public void setContentType(String contentType) { this.contentType = contentType; }

    public LocalDateTime getUploadDate() { return uploadDate; }
    public void setUploadDate(LocalDateTime uploadDate) { this.uploadDate = uploadDate; }

    public boolean isVerified() { return verified; }
    public void setVerified(boolean verified) { this.verified = verified; }

    public LocalDateTime getVerificationDate() { return verificationDate; }
    public void setVerificationDate(LocalDateTime verificationDate) { this.verificationDate = verificationDate; }

    public String getVerifiedBy() { return verifiedBy; }
    public void setVerifiedBy(String verifiedBy) { this.verifiedBy = verifiedBy; }
}