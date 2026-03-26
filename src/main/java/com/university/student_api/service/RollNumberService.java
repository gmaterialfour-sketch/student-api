package com.university.student_api.service;

import com.university.student_api.entity.RollNumberSequence;
import com.university.student_api.repository.RollNumberSequenceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class RollNumberService {

    @Autowired
    private RollNumberSequenceRepository sequenceRepository;

    @Transactional
    public synchronized String generateRollNumber(String departmentCode) {
        RollNumberSequence seq = sequenceRepository.findByDepartmentCode(departmentCode)
                .orElse(new RollNumberSequence(departmentCode, 0));
        long nextValue = seq.getCurrentValue() + 1;
        seq.setCurrentValue(nextValue);
        sequenceRepository.save(seq);
        return departmentCode + String.format("%04d", nextValue);
    }
}