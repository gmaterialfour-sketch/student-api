package com.university.student_api.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "roll_number_sequence")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class RollNumberSequence {
    @Id
    private Long id; // always 1 (single row)
    private Long currentValue;
}