package com.university.student_api.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "courses")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Course {
    @Id
    private String code;
    private String title;
    private int credits;
    private String description;
    
    @ManyToOne
    @JoinColumn(name = "department_code")
    private Department department;
    
    private String teacher1;   // first teacher name
    private String teacher2;   // second teacher name
}