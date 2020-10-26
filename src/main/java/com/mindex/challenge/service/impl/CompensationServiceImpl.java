package com.mindex.challenge.service.impl;

import com.mindex.challenge.dao.CompensationRepository;
import com.mindex.challenge.data.Compensation;
import com.mindex.challenge.service.CompensationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class CompensationServiceImpl implements CompensationService {
    @Autowired
    private CompensationRepository compensationRepository;

    @Override
    public Compensation create(Compensation comp) {
        return compensationRepository.insert(comp);
    }

    @Override
    public Compensation read(String employeeId) {
        Objects.requireNonNull(employeeId);
        return compensationRepository.findCompensationByEmployeeEmployeeId(employeeId);
    }
}
