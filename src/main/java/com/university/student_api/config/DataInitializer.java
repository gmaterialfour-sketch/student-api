package com.university.student_api.config;

import com.university.student_api.entity.*;
import com.university.student_api.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class DataInitializer implements CommandLineRunner {

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private DepartmentRepository departmentRepository;

    @Autowired
    private CourseRepository courseRepository;

    @Autowired
    private RollNumberSequenceRepository sequenceRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) throws Exception {
        // Create departments if none exist
        if (departmentRepository.count() == 0) {
            Department cs = new Department();
            cs.setCode("CS");
            cs.setName("Computer Science");
            departmentRepository.save(cs);

            Department eee = new Department();
            eee.setCode("EEE");
            eee.setName("Electrical & Electronics Engineering");
            departmentRepository.save(eee);

            Department mech = new Department();
            mech.setCode("ME");
            mech.setName("Mechanical Engineering");
            departmentRepository.save(mech);

            // Roll number sequences
            sequenceRepository.save(new RollNumberSequence("CS", 0));
            sequenceRepository.save(new RollNumberSequence("EEE", 0));
            sequenceRepository.save(new RollNumberSequence("ME", 0));

            // Courses for CS
            Course java = new Course();
            java.setCode("CS101");
            java.setTitle("Java Programming");
            java.setDescription("Object-oriented programming with Java");
            java.setCredits(4);
            java.setTeacher1("Dr. Smith");
            java.setTeacher2("Prof. Johnson");
            java.setDepartment(cs);
            courseRepository.save(java);

            Course web = new Course();
            web.setCode("CS102");
            web.setTitle("Web Development");
            web.setDescription("HTML, CSS, JavaScript");
            web.setCredits(3);
            web.setTeacher1("Dr. Lee");
            web.setDepartment(cs);
            courseRepository.save(web);

            // Courses for EEE
            Course circuits = new Course();
            circuits.setCode("EEE201");
            circuits.setTitle("Circuit Theory");
            circuits.setDescription("Electrical circuit analysis");
            circuits.setCredits(4);
            circuits.setTeacher1("Prof. Kumar");
            circuits.setDepartment(eee);
            courseRepository.save(circuits);

            Course machines = new Course();
            machines.setCode("EEE202");
            machines.setTitle("Electrical Machines");
            machines.setDescription("AC/DC machines");
            machines.setCredits(4);
            machines.setTeacher1("Dr. Rajesh");
            machines.setDepartment(eee);
            courseRepository.save(machines);

            // Courses for MECH
            Course thermo = new Course();
            thermo.setCode("ME301");
            thermo.setTitle("Thermodynamics");
            thermo.setDescription("Heat and work");
            thermo.setCredits(4);
            thermo.setTeacher1("Dr. Sharma");
            thermo.setDepartment(mech);
            courseRepository.save(thermo);

            Course fluid = new Course();
            fluid.setCode("ME302");
            fluid.setTitle("Fluid Mechanics");
            fluid.setDescription("Fluid dynamics");
            fluid.setCredits(4);
            fluid.setTeacher1("Prof. Gupta");
            fluid.setDepartment(mech);
            courseRepository.save(fluid);
        }

        // Create admin users if none exist
        if (studentRepository.count() == 0) {
            for (int i = 1; i <= 5; i++) {
                Student admin = new Student();
                admin.setRollNumber("ADMIN00" + i);
                admin.setName("Admin " + i);
                admin.setFullName("Administrator " + i);
                admin.setEmail("admin" + i + "@university.com");
                admin.setPassword(passwordEncoder.encode("admin" + i + "@2026"));
                admin.setRole(Role.ADMIN);
                admin.setFailedLoginAttempts(0);
                admin.setAadhaarNumber("10000000" + i);
                admin.setAcademicYear(2026);
                admin.setAddress("Admin Office");
                admin.setGender("Other");
                admin.setQualificationType(QualificationType.TWELFTH);
                admin.setTenthBoard("N/A");
                admin.setTenthYear(0);
                admin.setTenthPercentage(0.0);
                admin.setTwelfthBoard("N/A");
                admin.setTwelfthYear(0);
                admin.setTwelfthPercentage(0.0);
                admin.setTwelfthStream("N/A");
                admin.setItiTrade("N/A");
                admin.setItiYear(0);
                admin.setItiPercentage(0.0);
                admin.setDiplomaBranch("N/A");
                admin.setDiplomaCredits(0);
                admin.setDiplomaYear(0);
                admin.setDiplomaPercentage(0.0);
                admin.setSelectedCourses(null);
                studentRepository.save(admin);
            }
        }
    }
}