package com.insurance.application.controller;

import com.insurance.application.dto.CompanyDto;
import com.insurance.application.service.CompanyService;
import com.insurance.application.utils.Common;
import com.insurance.application.utils.Constant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@RestController
@RequestMapping("/info_company")
public class CompanyRestController {
    private CompanyService companyService;

    @Autowired
    public CompanyRestController(CompanyService companyService) {
        this.companyService = companyService;
    }

    @GetMapping
    private ResponseEntity<CompanyDto> getInfoCompany(HttpServletRequest request) {
        String companyId = request.getParameter("company");
        CompanyDto company = companyService.findCompanyById(Common.convertInt(companyId));
        return ResponseEntity.ok(company);
    }
}
