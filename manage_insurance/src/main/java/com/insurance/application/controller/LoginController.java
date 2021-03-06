package com.insurance.application.controller;

import com.insurance.application.service.impl.UserDetailsServiceImpl;
import com.insurance.application.utils.Constant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping(value = {Constant.START_REQUEST, Constant.LOGIN_ANNOTATION})
@PropertySource("classpath:messages_vi.properties")
public class LoginController {
    @Value("${AlreadyExist.insurance.number}")
    private String name;
    @GetMapping
    public String loginPage() {
        System.out.println(name);
        return Constant.PAGE_LOGIN;
    }
}
