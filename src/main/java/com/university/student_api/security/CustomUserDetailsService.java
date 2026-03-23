package com.university.student_api.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.university.student_api.entity.Student;
import com.university.student_api.repository.StudentRepository;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private StudentRepository studentRepository;

    @Override
    public UserDetails loadUserByUsername(String rollNumber) throws UsernameNotFoundException {
        Student student = studentRepository.findById(rollNumber)
                .orElseThrow(() -> new UsernameNotFoundException("User not found: " + rollNumber));

        return org.springframework.security.core.userdetails.User
                .withUsername(student.getRollNumber())
                .password(student.getPassword())
                .roles(student.getRole().replace("ROLE_", ""))
                .build();
    }
}