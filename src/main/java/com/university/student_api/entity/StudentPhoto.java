package com.university.student_api.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "student_photos")
public class StudentPhoto {

    @Id
    private String studentRollNumber;

    @Lob
    private byte[] imageData;

    private String contentType;

    // Constructors
    public StudentPhoto() {}

    public StudentPhoto(String studentRollNumber, byte[] imageData, String contentType) {
        this.studentRollNumber = studentRollNumber;
        this.imageData = imageData;
        this.contentType = contentType;
    }

    // Getters and setters
    public String getStudentRollNumber() { return studentRollNumber; }
    public void setStudentRollNumber(String studentRollNumber) { this.studentRollNumber = studentRollNumber; }

    public byte[] getImageData() { return imageData; }
    public void setImageData(byte[] imageData) { this.imageData = imageData; }

    public String getContentType() { return contentType; }
    public void setContentType(String contentType) { this.contentType = contentType; }
}