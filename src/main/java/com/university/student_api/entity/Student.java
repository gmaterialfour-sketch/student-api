package com.university.student_api.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "students")
public class Student {

    @Id
    private String rollNumber;

    @Column(unique = true, nullable = false)
    private String aadhaarNumber;

    private String name;
    private String fullName;
    private String address;
    private String gender;

    @ManyToOne
    @JoinColumn(name = "department_code")
    private Department department;

    private String selectedCourse;
    private String email;
    private int academicYear;

    @JsonIgnore
    private String password;

    private String role;

    @JsonIgnore
    private int failedLoginAttempts = 0;

    @JsonIgnore
    private LocalDateTime lastFailedLoginTime;

    // Constructors
    public Student() {}

    // Getters and setters
    public String getRollNumber() { return rollNumber; }
    public void setRollNumber(String rollNumber) { this.rollNumber = rollNumber; }

    public String getAadhaarNumber() { return aadhaarNumber; }
    public void setAadhaarNumber(String aadhaarNumber) { this.aadhaarNumber = aadhaarNumber; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getFullName() { return fullName; }
    public void setFullName(String fullName) { this.fullName = fullName; }

    public String getAddress() { return address; }
    public void setAddress(String address) { this.address = address; }

    public String getGender() { return gender; }
    public void setGender(String gender) { this.gender = gender; }

    public Department getDepartment() { return department; }
    public void setDepartment(Department department) { this.department = department; }

    public String getSelectedCourse() { return selectedCourse; }
    public void setSelectedCourse(String selectedCourse) { this.selectedCourse = selectedCourse; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public int getAcademicYear() { return academicYear; }
    public void setAcademicYear(int academicYear) { this.academicYear = academicYear; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

    public String getRole() { return role; }
    public void setRole(String role) { this.role = role; }

    public int getFailedLoginAttempts() { return failedLoginAttempts; }
    public void setFailedLoginAttempts(int failedLoginAttempts) { this.failedLoginAttempts = failedLoginAttempts; }

    public LocalDateTime getLastFailedLoginTime() { return lastFailedLoginTime; }
    public void setLastFailedLoginTime(LocalDateTime lastFailedLoginTime) { this.lastFailedLoginTime = lastFailedLoginTime; }
}