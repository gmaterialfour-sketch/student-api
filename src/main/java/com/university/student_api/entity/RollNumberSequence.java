package com.university.student_api.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "roll_number_sequence")
public class RollNumberSequence {

    @Id
    private String departmentCode;
    private long currentValue;

    @Version
    private long version;

    public RollNumberSequence() {}

    public RollNumberSequence(String departmentCode, long currentValue) {
        this.departmentCode = departmentCode;
        this.currentValue = currentValue;
    }

    public String getDepartmentCode() { return departmentCode; }
    public void setDepartmentCode(String departmentCode) { this.departmentCode = departmentCode; }

    public long getCurrentValue() { return currentValue; }
    public void setCurrentValue(long currentValue) { this.currentValue = currentValue; }

    public long getVersion() { return version; }
    public void setVersion(long version) { this.version = version; }
}