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
    private RollNumberSequenceRepository rollNumberSequenceRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostConstruct
    public void init() {
        // Create departments
        Department cse = new Department("CSE", "Computer Science", null);
        Department eee = new Department("EEE", "Electrical Engineering", null);
        Department ece = new Department("ECE", "Electronics Engineering", null);
        departmentRepository.save(cse);
        departmentRepository.save(eee);
        departmentRepository.save(ece);

        // Courses for CSE
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

        // Courses for EEE
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

        // Courses for ECE
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

        // Roll number sequence
        if (!rollNumberSequenceRepository.existsById(1L)) {
            rollNumberSequenceRepository.save(new RollNumberSequence(1L, 1000L));
        }

        // Create 5 admin users
        for (int i = 1; i <= 5; i++) {
            String rollNumber = String.format("ADMIN%03d", i);
            String email = String.format("admin%d@university.edu", i);
            String password = "admin" + i + "@2026";
            String aadhaar = "ADMIN" + i + "_AADHAAR";

            if (!studentRepository.existsById(rollNumber)) {
                Student admin = new Student();
                admin.setRollNumber(rollNumber);
                admin.setAadhaarNumber(aadhaar);
                admin.setName("System");
                admin.setFullName("System Administrator " + i);
                admin.setAddress("University HQ");
                admin.setDepartment(cse);
                admin.setSelectedCourse("N/A");
                admin.setEmail(email);
                admin.setAcademicYear(0);
                admin.setPassword(passwordEncoder.encode(password));
                admin.setRole("ROLE_ADMIN");
                studentRepository.save(admin);
                System.out.println("Admin user created → rollNumber: " + rollNumber + ", password: " + password);
            }
        }
    }
}