package com.insurance.application.controller;

import com.insurance.application.dto.CompanyDto;
import com.insurance.application.dto.Response;
import com.insurance.application.exception.NotFoundException;
import com.insurance.application.form.InsuranceForm;
import com.insurance.application.service.CompanyService;
import com.insurance.application.service.UserRoleService;
import com.insurance.application.service.UserService;
import com.insurance.application.utils.Common;
import com.insurance.application.utils.Constant;
import com.insurance.application.validator.ValidateInsuranceForm;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Set;

/**
 * ChienDV
 */

@Controller
@RequestMapping(Constant.INSURANCE_ANNOTATION)
public class UpdateInsuranceController {

    private UserService userService;
    private CompanyService companyService;
    private ValidateInsuranceForm registerValidate;
    private UserRoleService userRoleService;


    @Autowired
    public UpdateInsuranceController(UserService userService,
                                     ValidateInsuranceForm registerValidate,
                                     CompanyService companyService,
                                     UserRoleService userRoleService) {
        this.userService = userService;
        this.registerValidate = registerValidate;
        this.companyService = companyService;
        this.userRoleService = userRoleService;
    }

    @ModelAttribute(value = "listCompany")
    private List<CompanyDto> companyList() {
        return Common.compareName(companyService.findAll());
    }

    @GetMapping("/update/{id}")
    public String updateGet(Model model, @PathVariable Integer id) throws Exception {
        try {
            if (ObjectUtils.isNotEmpty(id)) {
                boolean checkRole = false;
                InsuranceForm insuranceForm = userService.findById(id);
                Authentication auth = SecurityContextHolder.getContext().getAuthentication();
                Set<String> roles = userRoleService.findRoleNamesByUserName(auth.getName());
                checkRole = Common.checkRole(roles);
                if (checkRole && insuranceForm.getUsername().equals(auth.getName())) {
                    checkRole = true;
                }
                if (!checkRole) {
                    throw new AccessDeniedException("Bạn không có quyền truy cập");
                }
                insuranceForm.setBirthDate(Common.convertFromDateYMD(insuranceForm.getBirthDate()));
                insuranceForm.setStartDateInsurance(Common.convertFromDateYMD(insuranceForm.getStartDateInsurance()));
                insuranceForm.setEndDateInsurance(Common.convertFromDateYMD(insuranceForm.getEndDateInsurance()));
                model.addAttribute("id", id);
                model.addAttribute(Constant.INSURANCE_FORM, insuranceForm);
            }
        } catch (NotFoundException fx) {
            throw new NotFoundException("Error không có record tồn tại");
        } catch (Exception ex) {
            throw new Exception(ex.getMessage());
        }
        return Constant.PAGE_UPDATE;
    }

    @PostMapping("/update")
    public String updatePost(@ModelAttribute(Constant.INSURANCE_FORM) @Validated InsuranceForm insuranceForm,
                             BindingResult bindingResult,
                             Model model, HttpServletRequest request) throws Exception {
        Response response = new Response(true);
        try {
            insuranceForm.setType("update");
            registerValidate.validate(insuranceForm, bindingResult);
            if (bindingResult.hasErrors()) {
                model.addAttribute(Constant.LIST_COMPANY, companyList());
                response.setStatus(false);
            } else {
                String id = request.getParameter("id");
                userService.update(insuranceForm, Common.convertInt(id));
                model.addAttribute("response", response);
            }
        } catch (NotFoundException fx) {
            throw new NotFoundException("Error không có record tồn tại");
        } catch (Exception ex) {
            throw new Exception(ex.getMessage());
        }
        return Constant.PAGE_UPDATE;
    }

}
