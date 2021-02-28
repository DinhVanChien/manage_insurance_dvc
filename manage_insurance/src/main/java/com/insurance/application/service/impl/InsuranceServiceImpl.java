package com.insurance.application.service.impl;

import com.insurance.application.repository.InsuranceRepository;
import com.insurance.application.service.InsuranceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class InsuranceServiceImpl implements InsuranceService {
    @Autowired
    private InsuranceRepository insuranceRepository;

    @Override
    public boolean findInsuranceByInsuranceNumber(String insuranceNumber) {
        if (insuranceRepository.findInsuranceByInsuranceNumber(insuranceNumber) != null) {
            return true;
        }
        return false;
    }
}
