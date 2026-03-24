package com.university.student_api.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;

@Entity
@Table(name = "courses")
public class Course {

    @Id
    private String code;
    private String title;
    private int credits;
    private String description;

    @ManyToOne
    @JoinColumn(name = "department_code")
    @JsonIgnoreProperties("courses") // prevents recursion
    private Department department;

    private String teacher1;
    private String teacher2;

    // Constructors
    public Course() {}

    public Course(String code, String title, int credits, String description,
                  Department department, String teacher1, String teacher2) {
        this.code = code;
        this.title = title;
        this.credits = credits;
        this.description = description;
        this.department = department;
        this.teacher1 = teacher1;
        this.teacher2 = teacher2;
    }

    // Getters and setters
    public String getCode() { return code; }
    public void setCode(String code) { this.code = code; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public int getCredits() { return credits; }
    public void setCredits(int credits) { this.credits = credits; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public Department getDepartment() { return department; }
    public void setDepartment(Department department) { this.department = department; }

    public String getTeacher1() { return teacher1; }
    public void setTeacher1(String teacher1) { this.teacher1 = teacher1; }

    public String getTeacher2() { return teacher2; }
    public void setTeacher2(String teacher2) { this.teacher2 = teacher2; }
}