package com.insurance.application.controller;

import com.insurance.application.exception.NotFoundException;
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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Set;

@RequestMapping("/insurances")
@Controller
public class DetailInsuranceController {

    private UserService userService;
    private UserRoleService userRoleService;

    @Autowired
    public DetailInsuranceController(UserService userService, UserRoleService userRoleService) {
        this.userService = userService;
        this.userRoleService = userRoleService;
    }

    @GetMapping("detail/{id}")
    public String viewUser(@PathVariable("id") String id, Model model) throws Exception {
        try {
            boolean isRole = false;
            InsuranceForm insuranceForm = userService.findById(Common.convertInt(id));
            if (insuranceForm != null) {
                Authentication auth = SecurityContextHolder.getContext().getAuthentication();
                Set<String> roles = userRoleService.findRoleNamesByUserName(auth.getName());
                isRole = Common.checkRole(roles);
                if (!isRole && insuranceForm.getUsername().equals(auth.getName())) {
                    isRole = true;
                }
                System.out.println("role  " + roles);
                model.addAttribute("insurance", insuranceForm);
                model.addAttribute("role", isRole);
            }
        } catch (NotFoundException fx) {
            throw new NotFoundException("Error không có record tồn tại");
        } catch (Exception ex) {
            throw new Exception(ex.getMessage());
        }

        return Constant.PAGE_DETAIL;
    }
}
