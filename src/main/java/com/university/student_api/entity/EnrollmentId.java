package com.university.student_api.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class EnrollmentId implements Serializable {
    @Column(name = "course_code")
    private String courseCode;
    @Column(name = "student_roll_number")
    private String studentRollNumber;

    public EnrollmentId() {}
    public EnrollmentId(String courseCode, String studentRollNumber) {
        this.courseCode = courseCode;
        this.studentRollNumber = studentRollNumber;
    }

    public String getCourseCode() { return courseCode; }
    public void setCourseCode(String courseCode) { this.courseCode = courseCode; }
    public String getStudentRollNumber() { return studentRollNumber; }
    public void setStudentRollNumber(String studentRollNumber) { this.studentRollNumber = studentRollNumber; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        EnrollmentId that = (EnrollmentId) o;
        return Objects.equals(courseCode, that.courseCode) &&
               Objects.equals(studentRollNumber, that.studentRollNumber);
    }
    @Override
    public int hashCode() {
        return Objects.hash(courseCode, studentRollNumber);
    }
}