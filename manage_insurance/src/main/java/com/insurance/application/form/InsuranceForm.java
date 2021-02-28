package com.insurance.application.form;

import com.insurance.application.entity.User;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Temporal;

import static javax.persistence.TemporalType.DATE;

@Setter
@Getter
@NoArgsConstructor
public class InsuranceForm {
    private Integer id;
    private String numberInsurance;
    private String fullName;
    private String sex;
    private String username;
    private String password;
    private String birthDate;
    private String type;
    private Integer companyId;
    private boolean isCheck;
    private String isNewCompany;
    private String companyName;
    private String companyAddress;
    private String companyEmail;
    private String companyPhone;
    private String placeRegisterOfInsurance;
    private String startDateInsurance;
    private String endDateInsurance;

    public InsuranceForm(User user) {
        this.id = user.getId();
        this.numberInsurance = user.getInsuranceId().getInsuranceNumber();
        this.fullName = user.getFullName();
        this.sex = String.valueOf(user.getUserSexDivision());
        this.username = user.getUsername();
        this.password = user.getPassword();
        this.birthDate = String.valueOf(user.getBirthDate());
        this.companyId = user.getCompanyId().getId();
        this.companyName = user.getCompanyId().getName();
        this.companyAddress = user.getCompanyId().getAddress();
        this.companyEmail = user.getCompanyId().getEmail();
        this.companyPhone = user.getCompanyId().getTelephone();
        this.placeRegisterOfInsurance = user.getInsuranceId().getPlaceOfRegister();
        this.startDateInsurance = String.valueOf(user.getInsuranceId().getStartDate());
        this.endDateInsurance = String.valueOf(user.getInsuranceId().getEndDate());
    }
}


