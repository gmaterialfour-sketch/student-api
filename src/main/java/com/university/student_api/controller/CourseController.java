package com.university.student_api.controller;

import com.university.student_api.dto.CourseRequest;
import com.university.student_api.entity.Course;
import com.university.student_api.entity.Department;
import com.university.student_api.repository.CourseRepository;
import com.university.student_api.repository.DepartmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin/courses")
public class CourseController {

    @Autowired
    private CourseRepository courseRepository;

    @Autowired
    private DepartmentRepository departmentRepository;

    @PostMapping
    public ResponseEntity<Course> createCourse(@RequestBody CourseRequest req) {
        Department department = departmentRepository.findById(req.getDepartmentCode())
                .orElseThrow(() -> new RuntimeException("Department not found"));

        Course course = new Course();
        course.setCode(req.getCode());
        course.setTitle(req.getTitle());
        course.setCredits(req.getCredits());
        course.setDescription(req.getDescription());
        course.setDepartment(department);
        course.setTeacher1(req.getTeacher1());
        course.setTeacher2(req.getTeacher2());
        return ResponseEntity.ok(courseRepository.save(course));
    }

    @GetMapping
    public List<Course> listAllCourses() {
        return courseRepository.findAll();
    }

    @GetMapping("/by-department/{deptCode}")
    public List<Course> getCoursesByDepartment(@PathVariable String deptCode) {
        return courseRepository.findByDepartment_Code(deptCode);
    }

    @GetMapping("/{code}")
    public ResponseEntity<Course> getCourse(@PathVariable String code) {
        return courseRepository.findById(code)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{code}")
    public ResponseEntity<Course> updateCourse(@PathVariable String code, @RequestBody CourseRequest req) {
        return courseRepository.findById(code)
                .map(existing -> {
                    existing.setTitle(req.getTitle());
                    existing.setCredits(req.getCredits());
                    existing.setDescription(req.getDescription());
                    if (req.getDepartmentCode() != null) {
                        Department department = departmentRepository.findById(req.getDepartmentCode())
                                .orElseThrow(() -> new RuntimeException("Department not found"));
                        existing.setDepartment(department);
                    }
                    existing.setTeacher1(req.getTeacher1());
                    existing.setTeacher2(req.getTeacher2());
                    return ResponseEntity.ok(courseRepository.save(existing));
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{code}")
    public ResponseEntity<Void> deleteCourse(@PathVariable String code) {
        if (courseRepository.existsById(code)) {
            courseRepository.deleteById(code);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}