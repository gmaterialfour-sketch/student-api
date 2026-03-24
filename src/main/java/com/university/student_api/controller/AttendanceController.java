package com.university.student_api.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.university.student_api.dto.AttendanceRequest;
import com.university.student_api.entity.Attendance;
import com.university.student_api.entity.Course;
import com.university.student_api.entity.Student;
import com.university.student_api.repository.AttendanceRepository;
import com.university.student_api.repository.CourseRepository;
import com.university.student_api.repository.StudentRepository;

@RestController
@RequestMapping("/api")
public class AttendanceController {

    @Autowired private AttendanceRepository attendanceRepository;
    @Autowired private StudentRepository studentRepository;
    @Autowired private CourseRepository courseRepository;

    @GetMapping("/student/attendance")
    public List<Attendance> getMyAttendance() {
        String rollNumber = SecurityContextHolder.getContext().getAuthentication().getName();
        return attendanceRepository.findByStudentRollNumber(rollNumber);
    }

    @GetMapping("/admin/students/{rollNumber}/attendance")
    public List<Attendance> getStudentAttendance(@PathVariable String rollNumber) {
        return attendanceRepository.findByStudentRollNumber(rollNumber);
    }

    @PostMapping("/admin/attendance")
    public ResponseEntity<?> markAttendance(@RequestBody AttendanceRequest req) {
        Student student = studentRepository.findById(req.getStudentRollNumber())
                .orElseThrow(() -> new RuntimeException("Student not found"));
        Course course = courseRepository.findById(req.getCourseCode())
                .orElseThrow(() -> new RuntimeException("Course not found"));
        Attendance attendance = new Attendance();
        attendance.setStudent(student);
        attendance.setCourse(course);
        attendance.setDate(req.getDate());
        attendance.setPresent(req.isPresent());
        attendanceRepository.save(attendance);
        return ResponseEntity.ok("Attendance recorded");
    }

    @PutMapping("/admin/attendance/{id}")
    public ResponseEntity<?> updateAttendance(@PathVariable Long id, @RequestBody AttendanceRequest req) {
        Attendance attendance = attendanceRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Attendance not found"));
        attendance.setPresent(req.isPresent());
        attendanceRepository.save(attendance);
        return ResponseEntity.ok("Attendance updated");
    }

    @DeleteMapping("/admin/attendance/{id}")
    public ResponseEntity<?> deleteAttendance(@PathVariable Long id) {
        attendanceRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}