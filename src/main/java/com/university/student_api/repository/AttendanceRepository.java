package com.university.student_api.repository;

import com.university.student_api.entity.Attendance;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface AttendanceRepository extends JpaRepository<Attendance, Long> {
    // Finds all attendance records for a given student roll number
    List<Attendance> findByStudentRollNumber(String rollNumber);
}