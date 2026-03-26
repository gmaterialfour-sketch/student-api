package com.university.student_api.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "educational_qualifications")
public class EducationalQualification {

    @Id
    private String studentRollNumber;

    @Enumerated(EnumType.STRING)
    private QualificationType qualificationType;

    // 10th
    private String tenthBoard;
    private int tenthYear;
    private double tenthPercentage;

    // 12th (only for TWELFTH)
    private String twelfthBoard;
    private int twelfthYear;
    private double twelfthPercentage;
    private String twelfthStream;

    // ITI (only for ITI)
    private String itiTrade;
    private int itiYear;
    private double itiPercentage;

    // Diploma (only for DIPLOMA)
    private String diplomaBranch;
    private int diplomaCredits;
    private int diplomaYear;
    private double diplomaPercentage;

    private boolean verified;

    // Getters and setters (all)
    public String getStudentRollNumber() { return studentRollNumber; }
    public void setStudentRollNumber(String studentRollNumber) { this.studentRollNumber = studentRollNumber; }

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

    public boolean isVerified() { return verified; }
    public void setVerified(boolean verified) { this.verified = verified; }
}