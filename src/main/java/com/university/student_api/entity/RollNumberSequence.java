package com.university.student_api.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "roll_number_sequence")
public class RollNumberSequence {

    @Id
    private Long id;
    private Long currentValue;

    // Constructors
    public RollNumberSequence() {}

    public RollNumberSequence(Long id, Long currentValue) {
        this.id = id;
        this.currentValue = currentValue;
    }

    // Getters and setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Long getCurrentValue() { return currentValue; }
    public void setCurrentValue(Long currentValue) { this.currentValue = currentValue; }
}