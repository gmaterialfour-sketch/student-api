package com.university.student_api.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.university.student_api.entity.RollNumberSequence;

import jakarta.persistence.LockModeType;

@Repository
public interface RollNumberSequenceRepository extends JpaRepository<RollNumberSequence, Long> {
    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("select s from RollNumberSequence s where s.id = 1")
    Optional<RollNumberSequence> findSequenceForUpdate();
}