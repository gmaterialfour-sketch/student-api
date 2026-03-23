package com.university.student_api.controller;

import com.university.student_api.entity.Student;
import com.university.student_api.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/admin")
public class AdminController {

    @Autowired
    private StudentRepository studentRepository;

    @GetMapping("/students")
    public Page<Student> listStudents(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(required = false) String department) {
        Pageable pageable = PageRequest.of(page, size);
        if (department != null && !department.isEmpty()) {
            return studentRepository.findByDepartment_Code(department, pageable);
        }
        return studentRepository.findAll(pageable);
    }

    @GetMapping("/students/{rollNumber}")
    public ResponseEntity<Student> getStudent(@PathVariable String rollNumber) {
        return studentRepository.findById(rollNumber)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/students/{rollNumber}")
    public ResponseEntity<Student> updateStudent(
            @PathVariable String rollNumber,
            @RequestBody Student updatedStudent) {
        return studentRepository.findById(rollNumber)
                .map(existing -> {
                    existing.setName(updatedStudent.getName());
                    existing.setFullName(updatedStudent.getFullName());
                    existing.setEmail(updatedStudent.getEmail());
                    existing.setAddress(updatedStudent.getAddress());
                    existing.setDepartment(updatedStudent.getDepartment());
                    existing.setSelectedCourse(updatedStudent.getSelectedCourse());
                    existing.setAcademicYear(updatedStudent.getAcademicYear());
                    studentRepository.save(existing);
                    return ResponseEntity.ok(existing);
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/students/{rollNumber}")
    public ResponseEntity<Void> deleteStudent(@PathVariable String rollNumber) {
        if (studentRepository.existsById(rollNumber)) {
            studentRepository.deleteById(rollNumber);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}