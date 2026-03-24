package com.university.student_api.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "enrollments")
public class Enrollment {

    @EmbeddedId
    private EnrollmentId id;

    private LocalDateTime enrollmentDate;
    private String grade;

    @ManyToOne
    @MapsId("studentRollNumber")
    @JoinColumn(name = "student_roll_number")
    @JsonIgnore
    private Student student;

    @ManyToOne
    @MapsId("courseCode")
    @JoinColumn(name = "course_code")
    @JsonIgnore
    private Course course;

    // Constructors, getters, setters
    public Enrollment() {}

    public Enrollment(EnrollmentId id, LocalDateTime enrollmentDate, String grade,
                      Student student, Course course) {
        this.id = id;
        this.enrollmentDate = enrollmentDate;
        this.grade = grade;
        this.student = student;
        this.course = course;
    }

    public EnrollmentId getId() { return id; }
    public void setId(EnrollmentId id) { this.id = id; }

    public LocalDateTime getEnrollmentDate() { return enrollmentDate; }
    public void setEnrollmentDate(LocalDateTime enrollmentDate) { this.enrollmentDate = enrollmentDate; }

    public String getGrade() { return grade; }
    public void setGrade(String grade) { this.grade = grade; }

    public Student getStudent() { return student; }
    public void setStudent(Student student) { this.student = student; }

    public Course getCourse() { return course; }
    public void setCourse(Course course) { this.course = course; }
}