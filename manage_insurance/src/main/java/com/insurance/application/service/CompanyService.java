package com.insurance.application.service;

import com.insurance.application.dto.CompanyDto;

import java.util.List;

public interface CompanyService {
    List<CompanyDto> findAll();

    CompanyDto findCompanyById(Integer id);
}
