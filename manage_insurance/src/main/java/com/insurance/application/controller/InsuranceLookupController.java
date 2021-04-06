package com.insurance.application.controller;

import com.insurance.application.form.InsuranceForm;
import com.insurance.application.service.UserRoleService;
import com.insurance.application.service.UserService;
import com.insurance.application.utils.Common;
import com.insurance.application.utils.Constant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.Set;

@Controller
@RequestMapping(Constant.INSURANCE_LOOKUP)
public class InsuranceLookupController {

    private UserService userService;

    @Autowired
    public InsuranceLookupController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public String insuranceLookup(Model model) {
       InsuranceForm insuranceForm = new InsuranceForm();
        if (insuranceForm != null) {
            model.addAttribute("insurance", insuranceForm);
        }
        return "insuranceLookup";
    }

    @PostMapping
    public String insuranceLookup(HttpServletRequest request, Model model) throws Exception {
        try {
            InsuranceForm insuranceForm;
            String insuNumber = request.getParameter("numberInsurance");
            String insuName = request.getParameter("fullName");
            insuranceForm = userService.findByInsuNumberAndFullName(insuNumber, insuName);
            if(insuranceForm == null) {
                model.addAttribute("message", "Không tồn tại bảo hiểm");
                insuranceForm = new InsuranceForm();
            }
            model.addAttribute("insurance", insuranceForm);
            return "insuranceLookup";
        } catch (Exception ex) {
            throw new Exception(ex.getMessage());
        }
    }
}
