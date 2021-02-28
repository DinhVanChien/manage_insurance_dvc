package com.insurance.application.controller;

import com.insurance.application.dto.CompanyDto;
import com.insurance.application.dto.Response;
import com.insurance.application.form.InsuranceForm;
import com.insurance.application.service.CompanyService;
import com.insurance.application.service.UserService;
import com.insurance.application.utils.Common;
import com.insurance.application.utils.Constant;
import com.insurance.application.validator.ValidateInsuranceForm;
import com.insurance.application.exception.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping(Constant.INSURANCE_ANNOTATION)
public class AddInsuranceController {

    private UserService userService;
    private ValidateInsuranceForm registerValidate;
    private CompanyService companyService;

    @Autowired
    public AddInsuranceController(UserService userService, ValidateInsuranceForm registerValidate, CompanyService companyService) {
        this.userService = userService;
        this.registerValidate = registerValidate;
        this.companyService = companyService;
    }

    @ModelAttribute(value = "listCompany")
    private List<CompanyDto> companyList() {
        return Common.compareName(companyService.findAll());
    }

    @GetMapping("/add")
    public String pageRegisterInsurance(Model model) {
        try {
            InsuranceForm insuranceForm = new InsuranceForm();
            model.addAttribute(Constant.INSURANCE_FORM, insuranceForm);
        } catch (Exception ex) {
            new Exception(ex.getMessage());
        }
        return Constant.PAGE_ADD;
    }

    @PostMapping("/add")
    public String registerInsurance(@ModelAttribute(Constant.INSURANCE_FORM)
                                    @Validated InsuranceForm insuranceForm,
                                    BindingResult bindingResult,
                                    Model model) throws Exception {
        Response response = new Response(true);
        try {
            registerValidate.validate(insuranceForm, bindingResult);
            if (bindingResult.hasErrors()) {
                model.addAttribute(Constant.LIST_COMPANY, companyList());
                response.setStatus(false);
            } else {
                userService.insert(insuranceForm);
            }
        } catch (NotFoundException fx) {
            response.setStatus(false);
            throw new NotFoundException("Record không tồn tại");
        } catch (Exception ex) {
            response.setStatus(false);
            throw new Exception(ex.getMessage());
        }
        model.addAttribute("response", response);
        return Constant.PAGE_ADD;
    }
}
