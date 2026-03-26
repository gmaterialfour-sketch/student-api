package com.university.student_api.service;

import com.university.student_api.entity.Attendance;
import com.university.student_api.repository.AttendanceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class AttendanceService {
    @Autowired
    private AttendanceRepository attendanceRepository;

    public List<Attendance> findByStudentRollNumber(String rollNumber) {
        return attendanceRepository.findByStudentRollNumber(rollNumber);
    }
}