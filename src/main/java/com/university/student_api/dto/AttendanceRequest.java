package com.university.student_api.dto;

import java.time.LocalDate;

public class AttendanceRequest {
    private String studentRollNumber;
    private String courseCode;
    private LocalDate date;
    private boolean present;
    // getters/setters
    public String getStudentRollNumber() {
        return studentRollNumber;
    }
    public void setStudentRollNumber(String studentRollNumber) {
        this.studentRollNumber = studentRollNumber;
    }
    public String getCourseCode() {
        return courseCode;
    }
    public void setCourseCode(String courseCode) {
        this.courseCode = courseCode;
    }
    public LocalDate getDate() {
        return date;
    }
    public void setDate(LocalDate date) {
        this.date = date;
    }
    public boolean isPresent() {
        return present;
    }
    public void setPresent(boolean present) {
        this.present = present;
    }
}