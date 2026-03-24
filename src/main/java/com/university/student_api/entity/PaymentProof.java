package com.university.student_api.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "payment_proofs")
public class PaymentProof {

    @Id
    private String studentRollNumber;

    @Lob
    private byte[] pdfData;

    private String fileName;
    private String contentType;
    private LocalDateTime uploadDate;
    private boolean approved = false;
    private LocalDateTime approvalDate;
    private String approvedBy;

    // Constructors, getters, setters
    public PaymentProof() {}

    public String getStudentRollNumber() { return studentRollNumber; }
    public void setStudentRollNumber(String studentRollNumber) { this.studentRollNumber = studentRollNumber; }

    public byte[] getPdfData() { return pdfData; }
    public void setPdfData(byte[] pdfData) { this.pdfData = pdfData; }

    public String getFileName() { return fileName; }
    public void setFileName(String fileName) { this.fileName = fileName; }

    public String getContentType() { return contentType; }
    public void setContentType(String contentType) { this.contentType = contentType; }

    public LocalDateTime getUploadDate() { return uploadDate; }
    public void setUploadDate(LocalDateTime uploadDate) { this.uploadDate = uploadDate; }

    public boolean isApproved() { return approved; }
    public void setApproved(boolean approved) { this.approved = approved; }

    public LocalDateTime getApprovalDate() { return approvalDate; }
    public void setApprovalDate(LocalDateTime approvalDate) { this.approvalDate = approvalDate; }

    public String getApprovedBy() { return approvedBy; }
    public void setApprovedBy(String approvedBy) { this.approvedBy = approvedBy; }
}