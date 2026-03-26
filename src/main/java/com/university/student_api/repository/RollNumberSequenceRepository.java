package com.university.student_api.repository;

import com.university.student_api.entity.RollNumberSequence;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface RollNumberSequenceRepository extends JpaRepository<RollNumberSequence, String> {
    Optional<RollNumberSequence> findByDepartmentCode(String departmentCode);
}