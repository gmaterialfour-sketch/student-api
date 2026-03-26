package com.university.student_api.service;

import com.university.student_api.entity.Student;
import com.university.student_api.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class StudentService {
    @Autowired
    private StudentRepository studentRepository;

    public Student findByRollNumber(String rollNumber) {
    return studentRepository.findById(rollNumber).orElse(null);
}

    public Student save(Student student) {
        return studentRepository.save(student);
    }
}