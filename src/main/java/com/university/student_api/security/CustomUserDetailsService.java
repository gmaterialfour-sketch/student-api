package com.university.student_api.security;

import com.university.student_api.entity.Student;
import com.university.student_api.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private StudentRepository studentRepository;

    @Override
    public UserDetails loadUserByUsername(String rollNumber) throws UsernameNotFoundException {
        Student student = studentRepository.findById(rollNumber)
                .orElseThrow(() -> new UsernameNotFoundException("Student not found with roll number: " + rollNumber));

        return User.builder()
                .username(student.getRollNumber())
                .password(student.getPassword())
                .roles(student.getRole().name())
                .build();
    }
}