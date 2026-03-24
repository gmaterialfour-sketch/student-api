package com.university.student_api.dto;

import java.util.List;

public class RegisterRequest {
    private String aadhaarNumber;
    private String name;
    private String fullName;
    private String address;
    private String gender;               // NEW
    private String departmentCode;
    private List<String> selectedCourses; // NEW: list of course codes
    private String password;
    private String email;
    private int academicYear;

    // Getters and setters
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

    public String getDepartmentCode() { return departmentCode; }
    public void setDepartmentCode(String departmentCode) { this.departmentCode = departmentCode; }

    public List<String> getSelectedCourses() { return selectedCourses; }
    public void setSelectedCourses(List<String> selectedCourses) { this.selectedCourses = selectedCourses; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public int getAcademicYear() { return academicYear; }
    public void setAcademicYear(int academicYear) { this.academicYear = academicYear; }
}