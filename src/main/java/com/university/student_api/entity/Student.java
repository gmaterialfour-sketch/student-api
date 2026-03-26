package com.university.student_api.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "students")
public class Student {

    @Id
    private String rollNumber;

    @Column(unique = true, nullable = false)
    private String aadhaarNumber;

    private int academicYear;
    private String address;
    private String email;
    private int failedLoginAttempts;
    private String fullName;
    private String gender;
    private LocalDateTime lastFailedLoginTime;
    private String name;
    private String password;

    @Enumerated(EnumType.STRING)
    private Role role;

    private String selectedCourse;  // legacy

    @ElementCollection
    private List<String> selectedCourses;

    @ManyToOne
    @JoinColumn(name = "department_code")
    private Department department;

    @Transient
    private String departmentCode;   // transient for registration

    // Qualification fields
    @Enumerated(EnumType.STRING)
    private QualificationType qualificationType;

    private String tenthBoard;
    private int tenthYear;
    private double tenthPercentage;

    private String twelfthBoard;
    private int twelfthYear;
    private double twelfthPercentage;
    private String twelfthStream;

    private String itiTrade;
    private int itiYear;
    private double itiPercentage;

    private String diplomaBranch;
    private int diplomaCredits;
    private int diplomaYear;
    private double diplomaPercentage;

    // Getters and setters
    public String getRollNumber() { return rollNumber; }
    public void setRollNumber(String rollNumber) { this.rollNumber = rollNumber; }

    public String getAadhaarNumber() { return aadhaarNumber; }
    public void setAadhaarNumber(String aadhaarNumber) { this.aadhaarNumber = aadhaarNumber; }

    public int getAcademicYear() { return academicYear; }
    public void setAcademicYear(int academicYear) { this.academicYear = academicYear; }

    public String getAddress() { return address; }
    public void setAddress(String address) { this.address = address; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public int getFailedLoginAttempts() { return failedLoginAttempts; }
    public void setFailedLoginAttempts(int failedLoginAttempts) { this.failedLoginAttempts = failedLoginAttempts; }

    public String getFullName() { return fullName; }
    public void setFullName(String fullName) { this.fullName = fullName; }

    public String getGender() { return gender; }
    public void setGender(String gender) { this.gender = gender; }

    public LocalDateTime getLastFailedLoginTime() { return lastFailedLoginTime; }
    public void setLastFailedLoginTime(LocalDateTime lastFailedLoginTime) { this.lastFailedLoginTime = lastFailedLoginTime; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

    public Role getRole() { return role; }
    public void setRole(Role role) { this.role = role; }

    public String getSelectedCourse() { return selectedCourse; }
    public void setSelectedCourse(String selectedCourse) { this.selectedCourse = selectedCourse; }

    public List<String> getSelectedCourses() { return selectedCourses; }
    public void setSelectedCourses(List<String> selectedCourses) { this.selectedCourses = selectedCourses; }

    public Department getDepartment() { return department; }
    public void setDepartment(Department department) { this.department = department; }

    public String getDepartmentCode() { return departmentCode; }
    public void setDepartmentCode(String departmentCode) { this.departmentCode = departmentCode; }

    public QualificationType getQualificationType() { return qualificationType; }
    public void setQualificationType(QualificationType qualificationType) { this.qualificationType = qualificationType; }

    public String getTenthBoard() { return tenthBoard; }
    public void setTenthBoard(String tenthBoard) { this.tenthBoard = tenthBoard; }

    public int getTenthYear() { return tenthYear; }
    public void setTenthYear(int tenthYear) { this.tenthYear = tenthYear; }

    public double getTenthPercentage() { return tenthPercentage; }
    public void setTenthPercentage(double tenthPercentage) { this.tenthPercentage = tenthPercentage; }

    public String getTwelfthBoard() { return twelfthBoard; }
    public void setTwelfthBoard(String twelfthBoard) { this.twelfthBoard = twelfthBoard; }

    public int getTwelfthYear() { return twelfthYear; }
    public void setTwelfthYear(int twelfthYear) { this.twelfthYear = twelfthYear; }

    public double getTwelfthPercentage() { return twelfthPercentage; }
    public void setTwelfthPercentage(double twelfthPercentage) { this.twelfthPercentage = twelfthPercentage; }

    public String getTwelfthStream() { return twelfthStream; }
    public void setTwelfthStream(String twelfthStream) { this.twelfthStream = twelfthStream; }

    public String getItiTrade() { return itiTrade; }
    public void setItiTrade(String itiTrade) { this.itiTrade = itiTrade; }

    public int getItiYear() { return itiYear; }
    public void setItiYear(int itiYear) { this.itiYear = itiYear; }

    public double getItiPercentage() { return itiPercentage; }
    public void setItiPercentage(double itiPercentage) { this.itiPercentage = itiPercentage; }

    public String getDiplomaBranch() { return diplomaBranch; }
    public void setDiplomaBranch(String diplomaBranch) { this.diplomaBranch = diplomaBranch; }

    public int getDiplomaCredits() { return diplomaCredits; }
    public void setDiplomaCredits(int diplomaCredits) { this.diplomaCredits = diplomaCredits; }

    public int getDiplomaYear() { return diplomaYear; }
    public void setDiplomaYear(int diplomaYear) { this.diplomaYear = diplomaYear; }

    public double getDiplomaPercentage() { return diplomaPercentage; }
    public void setDiplomaPercentage(double diplomaPercentage) { this.diplomaPercentage = diplomaPercentage; }
}