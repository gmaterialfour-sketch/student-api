package com.university.student_api.controller;

import com.university.student_api.entity.Course;
import com.university.student_api.repository.CourseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

@RestController
@RequestMapping("/api/public/courses")
public class CourseController {

    @Autowired
    private CourseRepository courseRepository;

    @GetMapping("/by-department/{deptCode}")
    public List<Course> getCoursesByDepartment(@PathVariable String deptCode) {
        return courseRepository.findByDepartment_Code(deptCode);
    }
}