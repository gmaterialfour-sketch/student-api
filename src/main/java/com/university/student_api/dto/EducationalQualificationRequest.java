package com.university.student_api.dto;

public class EducationalQualificationRequest {
    private String tenthBoard;
    private int tenthYear;
    private double tenthPercentage;
    private String qualificationType; // "12TH", "DIPLOMA", "ITI"
    // 12th fields
    private String twelfthBoard;
    private int twelfthYear;
    private double twelfthPercentage;
    // Diploma fields
    private String diplomaBranch;
    private int diplomaCredits;
    // ITI fields
    private String itiTrade;
    private int itiYear;
    private double itiPercentage;
    // getters/setters
    public String getTenthBoard() {
        return tenthBoard;
    }
    public void setTenthBoard(String tenthBoard) {
        this.tenthBoard = tenthBoard;
    }
    public int getTenthYear() {
        return tenthYear;
    }
    public void setTenthYear(int tenthYear) {
        this.tenthYear = tenthYear;
    }
    public double getTenthPercentage() {
        return tenthPercentage;
    }
    public void setTenthPercentage(double tenthPercentage) {
        this.tenthPercentage = tenthPercentage;
    }
    public String getQualificationType() {
        return qualificationType;
    }
    public void setQualificationType(String qualificationType) {
        this.qualificationType = qualificationType;
    }
    public String getTwelfthBoard() {
        return twelfthBoard;
    }
    public void setTwelfthBoard(String twelfthBoard) {
        this.twelfthBoard = twelfthBoard;
    }
    public int getTwelfthYear() {
        return twelfthYear;
    }
    public void setTwelfthYear(int twelfthYear) {
        this.twelfthYear = twelfthYear;
    }
    public double getTwelfthPercentage() {
        return twelfthPercentage;
    }
    public void setTwelfthPercentage(double twelfthPercentage) {
        this.twelfthPercentage = twelfthPercentage;
    }
    public String getDiplomaBranch() {
        return diplomaBranch;
    }
    public void setDiplomaBranch(String diplomaBranch) {
        this.diplomaBranch = diplomaBranch;
    }
    public int getDiplomaCredits() {
        return diplomaCredits;
    }
    public void setDiplomaCredits(int diplomaCredits) {
        this.diplomaCredits = diplomaCredits;
    }
    public String getItiTrade() {
        return itiTrade;
    }
    public void setItiTrade(String itiTrade) {
        this.itiTrade = itiTrade;
    }
    public int getItiYear() {
        return itiYear;
    }
    public void setItiYear(int itiYear) {
        this.itiYear = itiYear;
    }
    public double getItiPercentage() {
        return itiPercentage;
    }
    public void setItiPercentage(double itiPercentage) {
        this.itiPercentage = itiPercentage;
    }
}