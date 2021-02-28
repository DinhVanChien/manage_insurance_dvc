package com.insurance.application.repository;

import com.insurance.application.entity.Insurance;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InsuranceRepository extends JpaRepository<Insurance, Integer> {
    Insurance save(Insurance insurance);

    Insurance findInsuranceById(Integer id);

    Insurance findInsuranceByInsuranceNumber(String insuranceNumber);
}
