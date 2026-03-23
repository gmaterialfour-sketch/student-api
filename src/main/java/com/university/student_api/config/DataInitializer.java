package com.university.student_api.config;

import com.university.student_api.entity.*;
import com.university.student_api.repository.*;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class DataInitializer {

    @Autowired
    private StudentRepository studentRepository;
    @Autowired
    private DepartmentRepository departmentRepository;
    @Autowired
    private CourseRepository courseRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostConstruct
    public void init() {
        // ---------- Create Departments ----------
        Department cse = new Department("CSE", "Computer Science", null);
        Department eee = new Department("EEE", "Electrical Engineering", null);
        Department ece = new Department("ECE", "Electronics Engineering", null);
        departmentRepository.save(cse);
        departmentRepository.save(eee);
        departmentRepository.save(ece);

        // ---------- Courses for CSE ----------
        Course cse1 = new Course("CS101", "Theory of Computation", 3, "TOC", cse, "Prof. Smith", "Prof. Johnson");
        Course cse2 = new Course("CS102", "Data Structures", 3, "DS", cse, "Prof. Brown", "Prof. Davis");
        Course cse3 = new Course("CS103", "Algorithms", 3, "Algo", cse, "Prof. Wilson", "Prof. Martinez");
        Course cse4 = new Course("CS104", "Operating Systems", 3, "OS", cse, "Prof. Anderson", "Prof. Taylor");
        Course cse5 = new Course("CS105", "Computer Networks", 3, "CN", cse, "Prof. Thomas", "Prof. Moore");
        courseRepository.save(cse1);
        courseRepository.save(cse2);
        courseRepository.save(cse3);
        courseRepository.save(cse4);
        courseRepository.save(cse5);

        // ---------- Courses for EEE ----------
        Course eee1 = new Course("EEE101", "Circuit Theory", 3, "CT", eee, "Prof. White", "Prof. Harris");
        Course eee2 = new Course("EEE102", "Electrical Machines", 3, "EM", eee, "Prof. Martin", "Prof. Thompson");
        Course eee3 = new Course("EEE103", "Power Systems", 3, "PS", eee, "Prof. Garcia", "Prof. Robinson");
        Course eee4 = new Course("EEE104", "Control Systems", 3, "CS", eee, "Prof. Clark", "Prof. Rodriguez");
        Course eee5 = new Course("EEE105", "Renewable Energy", 3, "RE", eee, "Prof. Lewis", "Prof. Lee");
        courseRepository.save(eee1);
        courseRepository.save(eee2);
        courseRepository.save(eee3);
        courseRepository.save(eee4);
        courseRepository.save(eee5);

        // ---------- Courses for ECE ----------
        Course ece1 = new Course("ECE101", "Digital Electronics", 3, "DE", ece, "Prof. Walker", "Prof. Hall");
        Course ece2 = new Course("ECE102", "Microprocessors", 3, "MP", ece, "Prof. Allen", "Prof. Young");
        Course ece3 = new Course("ECE103", "Communication Systems", 3, "CS", ece, "Prof. King", "Prof. Wright");
        Course ece4 = new Course("ECE104", "VLSI Design", 3, "VLSI", ece, "Prof. Scott", "Prof. Green");
        Course ece5 = new Course("ECE105", "Embedded Systems", 3, "ES", ece, "Prof. Baker", "Prof. Adams");
        courseRepository.save(ece1);
        courseRepository.save(ece2);
        courseRepository.save(ece3);
        courseRepository.save(ece4);
        courseRepository.save(ece5);

        // ---------- Admin user ----------
        if (!studentRepository.existsById("ADMIN001")) {
            Student admin = new Student();
            admin.setRollNumber("ADMIN001");
            admin.setAadhaarNumber("000000000000");
            admin.setName("System");
            admin.setFullName("System Administrator");
            admin.setAddress("University HQ");
            admin.setDepartment(cse); // assign any department
            admin.setSelectedCourse("N/A");
            admin.setPassword(passwordEncoder.encode("admin@2026"));
            admin.setRole("ROLE_ADMIN");
            studentRepository.save(admin);
            System.out.println("Admin user created → rollNumber: ADMIN001 , password: admin@2026");
        }
    }
}