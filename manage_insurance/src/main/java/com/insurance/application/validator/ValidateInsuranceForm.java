package com.insurance.application.validator;

import com.insurance.application.dto.CompanyDto;
import com.insurance.application.form.InsuranceForm;
import com.insurance.application.service.CompanyService;
import com.insurance.application.service.InsuranceService;
import com.insurance.application.service.UserService;
import com.insurance.application.utils.Common;
import com.insurance.application.utils.Constant;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;

@Component
@PropertySource(value = "classpath:messages_vi.properties", encoding = "UTF-8")
public class ValidateInsuranceForm implements Validator {
    private InsuranceService insuranceService;
    private UserService userService;
    private CompanyService companyService;

    @Autowired
    public ValidateInsuranceForm(InsuranceService insuranceService, UserService userService, CompanyService companyService) {
        this.insuranceService = insuranceService;
        this.userService = userService;
        this.companyService = companyService;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return InsuranceForm.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        InsuranceForm insuranceForm = (InsuranceForm) target;
        Locale localeVi = new Locale("vi", "VN");
        ResourceBundle labels = ResourceBundle.getBundle("messages", localeVi);

        ValidationUtils.rejectIfEmptyOrWhitespace(errors,
                "fullName", Constant.NOT_EMPTY_FULLNAME,
                labels.getString(Constant.NOT_EMPTY_FULLNAME));

        ValidationUtils.rejectIfEmptyOrWhitespace(errors,
                "birthDate", Constant.NOT_EMPTY_BIRTHDAY,
                labels.getString(Constant.NOT_EMPTY_BIRTHDAY));

        ValidationUtils.rejectIfEmptyOrWhitespace(errors,
                "placeRegisterOfInsurance", Constant.NOT_EMPTY_INSURANCE_PLACE,
                labels.getString(Constant.NOT_EMPTY_INSURANCE_PLACE));

        ValidationUtils.rejectIfEmptyOrWhitespace(errors,
                "startDateInsurance", Constant.NOT_EMPTY_INSURANCE_DATE_START,
                labels.getString(Constant.NOT_EMPTY_INSURANCE_DATE_START));

        ValidationUtils.rejectIfEmptyOrWhitespace(errors,
                "endDateInsurance", Constant.NOT_EMPTY_INSURANCE_DATE_END,
                labels.getString(Constant.NOT_EMPTY_INSURANCE_DATE_END));

        if (Common.isNewCompany(insuranceForm.getIsNewCompany())) {
            ValidationUtils.rejectIfEmptyOrWhitespace(errors,
                    "companyName", Constant.NOT_EMPTY_COMPANY_NAME,
                    labels.getString(Constant.NOT_EMPTY_INSURANCE_NUMBER));
            ValidationUtils.rejectIfEmptyOrWhitespace(errors,
                    "companyAddress", Constant.NOT_EMPTY_COMPANY_ADDRESS,
                    labels.getString(Constant.NOT_EMPTY_COMPANY_ADDRESS));
            ValidationUtils.rejectIfEmptyOrWhitespace(errors,
                    "companyPhone", Constant.NOT_EMPTY_COMPANY_PHONE,
                    labels.getString(Constant.NOT_EMPTY_COMPANY_PHONE));
            ValidationUtils.rejectIfEmptyOrWhitespace(errors,
                    "companyEmail", Constant.NOT_EMPTY_COMPANY_EMAIL,
                    labels.getString(Constant.NOT_EMPTY_COMPANY_EMAIL));

            List<CompanyDto> companies = companyService.findAll();
            if (companies != null) {
                for (CompanyDto companyDto : companies) {
                    if (companyDto.getName().equals(insuranceForm.getCompanyName())) {
                        errors.rejectValue("companyName",
                                Constant.ALREADY_EXIST_COMPANY_NAME,
                                labels.getString(Constant.ALREADY_EXIST_COMPANY_NAME));
                    }
                    if (companyDto.getEmail().equals(insuranceForm.getCompanyEmail())) {
                        errors.rejectValue("companyEmail",
                                Constant.ALREADY_EXIST_COMPANY_EMAIL,
                                labels.getString(Constant.ALREADY_EXIST_COMPANY_EMAIL));
                    }
                    if (companyDto.getTelephone().equals(insuranceForm.getCompanyPhone())) {
                        errors.rejectValue("companyPhone",
                                Constant.ALREADY_EXIST_COMPANY_PHONE,
                                labels.getString(Constant.ALREADY_EXIST_COMPANY_PHONE));
                    }
                }
            }
            if (!Common.isEmail(insuranceForm.getCompanyEmail())) {
                errors.rejectValue("companyEmail",
                        Constant.FORMAT_COMPANY_EMAIL,
                        labels.getString(Constant.FORMAT_COMPANY_EMAIL));
            }
            if (!Common.isPhone(insuranceForm.getCompanyPhone())) {
                errors.rejectValue("companyPhone",
                        Constant.FORMAT_COMPANY_PHONE,
                        labels.getString(Constant.FORMAT_COMPANY_PHONE));
            }
        }

        // type = update se khong can validate
        if (StringUtils.isBlank(insuranceForm.getType())) {
            ValidationUtils.rejectIfEmptyOrWhitespace(errors,
                    "username", Constant.NOT_EMPTY_USERNAME,
                    labels.getString(Constant.NOT_EMPTY_USERNAME));
            ValidationUtils.rejectIfEmptyOrWhitespace(errors,
                    "numberInsurance", Constant.NOT_EMPTY_INSURANCE_NUMBER,
                    labels.getString(Constant.NOT_EMPTY_INSURANCE_NUMBER));
            if (userService.findByUsername(insuranceForm.getUsername()) != null) {
                errors.rejectValue("username",
                        Constant.ALREADY_EXIST_USERNAME,
                        labels.getString(Constant.ALREADY_EXIST_USERNAME));
            }

            if (insuranceForm.getNumberInsurance().length() > 0 &&
                    !Common.isNumber(insuranceForm.getNumberInsurance())) {
                errors.rejectValue("numberInsurance",
                        Constant.INSURANCE_NUMBER_FORMAT,
                        labels.getString(Constant.INSURANCE_NUMBER_FORMAT));
            } else if (insuranceForm.getNumberInsurance().length() > 0
                    && insuranceForm.getNumberInsurance().length() != 10) {
                errors.rejectValue("numberInsurance",
                        Constant.INSURANCE_NUMBER_LENGHT,
                        labels.getString(Constant.INSURANCE_NUMBER_LENGHT));
            } else {
                if (insuranceService.findInsuranceByInsuranceNumber(insuranceForm.getNumberInsurance())) {
                    errors.rejectValue("numberInsurance",
                            Constant.ALREADY_EXIST_INSURANCE_NUMBER,
                            labels.getString(Constant.ALREADY_EXIST_INSURANCE_NUMBER));
                }
            }
            insuranceForm.setType("");
        }
    }
}
