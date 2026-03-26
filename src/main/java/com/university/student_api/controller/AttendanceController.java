package com.university.student_api.controller;

import com.university.student_api.entity.Attendance;
import com.university.student_api.service.AttendanceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/api/attendance")
public class AttendanceController {
    @Autowired
    private AttendanceService attendanceService;

    @GetMapping("/student")
    public ResponseEntity<List<Attendance>> getMyAttendance(Principal principal) {
        String rollNumber = principal.getName();
        List<Attendance> records = attendanceService.findByStudentRollNumber(rollNumber);
        return ResponseEntity.ok(records);
    }
}