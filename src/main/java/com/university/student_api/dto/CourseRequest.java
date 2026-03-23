package com.university.student_api.dto;

public class CourseRequest {
    private String code;
    private String title;
    private int credits;
    private String description;
    private String departmentCode;
    private String teacher1;
    private String teacher2;

    // Getters and setters
    public String getCode() { return code; }
    public void setCode(String code) { this.code = code; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public int getCredits() { return credits; }
    public void setCredits(int credits) { this.credits = credits; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public String getDepartmentCode() { return departmentCode; }
    public void setDepartmentCode(String departmentCode) { this.departmentCode = departmentCode; }

    public String getTeacher1() { return teacher1; }
    public void setTeacher1(String teacher1) { this.teacher1 = teacher1; }

    public String getTeacher2() { return teacher2; }
    public void setTeacher2(String teacher2) { this.teacher2 = teacher2; }
}