package com.insurance.application.service.impl;

import com.insurance.application.repository.CompanyRepository;
import com.insurance.application.dto.CompanyDto;
import com.insurance.application.entity.Company;
import com.insurance.application.service.CompanyService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class CompanyServiceImpl implements CompanyService {

    private CompanyRepository companyRepository;
    private ModelMapper modelMapper;

    @Autowired
    public CompanyServiceImpl(CompanyRepository companyRepository, ModelMapper modelMapper) {
        this.companyRepository = companyRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public List<CompanyDto> findAll() {
        List<Company> companies = companyRepository.findAll();
        List<CompanyDto> companyDtoList = new ArrayList<CompanyDto>();
        if (companies != null) {
            for (Company company : companies) {
                companyDtoList.add(modelMapper.map(company, CompanyDto.class));
            }
        }
        return companyDtoList;
    }

    @Override
    public CompanyDto findCompanyById(Integer id) {
        Company company = companyRepository.findCompanyById(id);
        if (company != null) {
            CompanyDto companyDto = modelMapper.map(company, CompanyDto.class);
            return companyDto;
        }
        return null;
    }
}
