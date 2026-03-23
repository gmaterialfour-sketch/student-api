package com.university.student_api.entity;

import java.time.LocalDateTime;

import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "enrollments")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Enrollment {

    @EmbeddedId
    private EnrollmentId id;

    private LocalDateTime enrollmentDate;
    private String grade;

    @ManyToOne
    @MapsId("studentRollNumber")
    @JoinColumn(name = "student_roll_number")
    private Student student;

    @ManyToOne
    @MapsId("courseCode")
    @JoinColumn(name = "course_code")
    private Course course;
}