package com.insurance.application.form;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ErrorForm {
    private int id;
    private String numberInsurance;
    private String fullName;
    private String sex;
    private String username;
    private String password;
    private String birthDate;
    private int companyId;
    private String companyName;
    private String companyAddress;
    private String companyEmail;
    private String companyPhone;
    private String placeRegisterOfInsurance;
    private String startDateInsurance;
    private String endDateInsurance;
}
