package com.insurance.application.repository;

import com.insurance.application.entity.Company;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CompanyRepository extends JpaRepository<Company, Integer> {
    List<Company> findAll();

    Company save(Company company);

    Company findCompanyById(Integer id);
}
