package com.insurance.application.service.impl;

import com.insurance.application.repository.*;
import com.insurance.application.entity.Company;
import com.insurance.application.entity.Insurance;
import com.insurance.application.entity.User;
import com.insurance.application.form.InsuranceForm;
import com.insurance.application.service.UserService;
import com.insurance.application.utils.Common;
import org.apache.commons.lang3.ObjectUtils;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional(propagation = Propagation.SUPPORTS)
public class UserServiceImpl implements UserService {

    private UserRepository userRepository;
    private UserInsuranceRepository userRepositorySQL;
    private CompanyRepository companyRepository;
    private InsuranceRepository insuranceRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository,
                           UserInsuranceRepository userRepositorySQL,
                           CompanyRepository companyRepository,
                           InsuranceRepository insuranceRepository,
                           ModelMapper modelMapper) {
        this.userRepository = userRepository;
        this.userRepositorySQL = userRepositorySQL;
        this.companyRepository = companyRepository;
        this.insuranceRepository = insuranceRepository;
    }

    @Override
    public InsuranceForm findById(int id) {
        User user = userRepository.findById(id);
        if (user != null) {
            InsuranceForm inForm = new InsuranceForm(user);
            inForm.setBirthDate(Common.convertFromDateDMY(user.getBirthDate()));
            inForm.setEndDateInsurance(Common.convertFromDateDMY(user.getInsuranceId().getEndDate()));
            inForm.setStartDateInsurance(Common.convertFromDateDMY(user.getInsuranceId().getStartDate()));
            return inForm;
        }
        return null;
    }

    @Override
    public InsuranceForm findByUsername(String username) {
        User user = userRepository.findByUsername(username);
        if (user != null) {
            InsuranceForm inForm = new InsuranceForm(user);
            return inForm;
        }
        return null;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public boolean insert(InsuranceForm registerForm) throws Exception {
        boolean check = false;
        int companyId = 0;
        int insuranceId = 0;
        if (Common.isNewCompany(registerForm.getIsNewCompany())) {
            Company company = new Company();
            company.setName(registerForm.getCompanyName());
            company.setAddress(registerForm.getCompanyAddress());
            company.setEmail(registerForm.getCompanyEmail());
            company.setTelephone(registerForm.getCompanyPhone());
            companyId = companyRepository.save(company).getId();
        } else {
            companyId = registerForm.getCompanyId();
        }
        Insurance insurance = new Insurance();
        insurance.setInsuranceNumber(registerForm.getNumberInsurance());
        insurance.setPlaceOfRegister(registerForm.getPlaceRegisterOfInsurance());
        insurance.setStartDate(Common.convertFromDate(registerForm.getStartDateInsurance()));
        insurance.setEndDate(Common.convertFromDate(registerForm.getEndDateInsurance()));
        insuranceId = insuranceRepository.save(insurance).getId();

        if (companyId != 0 && insuranceId != 0) {
            User user = new User();
            user.setCompanyId(companyRepository.findCompanyById(companyId));
            user.setInsuranceId(insuranceRepository.findInsuranceById(insuranceId));
            user.setBirthDate(Common.convertFromDate(registerForm.getBirthDate()));
            user.setFullName(registerForm.getFullName());
            user.setUsername(registerForm.getUsername());
            user.setPassword(registerForm.getPassword());
            user.setUserSexDivision(registerForm.getSex().charAt(0));
            if (userRepository.save(user) != null) {
                check = true;
            }
        }
        if (!check) {
            throw new Exception("Thêm mới record thất bại");
        }
        return check;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public boolean update(InsuranceForm insuranceForm, int id) throws Exception {
        boolean check = false;
        int insuranceId = 0;
        User user = new User();
        if (ObjectUtils.isNotEmpty(id)) {
            user = userRepository.findById(id);
            Company company = new Company();
            if (Common.isNewCompany(insuranceForm.getIsNewCompany())) {
                company.setName(insuranceForm.getCompanyName());
                company.setAddress(insuranceForm.getCompanyAddress());
                company.setEmail(insuranceForm.getCompanyEmail());
                company.setTelephone(insuranceForm.getCompanyPhone());
                company = companyRepository.save(company);
            } else {
                company = companyRepository.findCompanyById(insuranceForm.getCompanyId());
            }
            Insurance insurance = user.getInsuranceId();
            insurance.setPlaceOfRegister(insuranceForm.getPlaceRegisterOfInsurance());
            insurance.setStartDate(Common.convertFromDate(insuranceForm.getStartDateInsurance()));
            insurance.setEndDate(Common.convertFromDate(insuranceForm.getEndDateInsurance()));
            insuranceId = insuranceRepository.save(insurance).getId();

            if (company != null && insuranceId != 0) {
                user.setCompanyId(company);
                user.setBirthDate(Common.convertFromDate(insuranceForm.getBirthDate()));
                user.setFullName(insuranceForm.getFullName());
                user.setUserSexDivision(insuranceForm.getSex().charAt(0));
                if (userRepository.save(user) != null) {
                    check = true;
                }
            } else {
                check = false;
            }
        }
        if (!check) {
            throw new Exception("Update record thất bại");
        }
        return check;
    }

    @Override
    public void deleteById(int id) {
        if (id != 0) {
            userRepository.deleteById(id);
        }
    }
    @Override
    public List<InsuranceForm> getListUser(int companyId,
                                           String name,
                                           String insuranceNumber,
                                           String placeOfRegister,
                                           String sortType,
                                           int page) {
        List<User> users = userRepositorySQL.getListUser(companyId, name, insuranceNumber, placeOfRegister, sortType, page);
        List<InsuranceForm> inForms = new ArrayList<InsuranceForm>();
        if (users != null) {
            for (User u : users) {
                InsuranceForm inForm = new InsuranceForm(u);
                inForm.setBirthDate(Common.convertFromDateDMY(u.getBirthDate()));
                inForm.setEndDateInsurance(Common.convertFromDateDMY(u.getInsuranceId().getEndDate()));
                inForm.setStartDateInsurance(Common.convertFromDateDMY(u.getInsuranceId().getStartDate()));
                inForms.add(inForm);
            }
            return inForms;
        }
        return null;
    }

    @Override
    public int getTotalUser(int companyId, String name, String insuranceNumber, String placeOfRegister) {
        return userRepositorySQL.getTotalUser(companyId, name, insuranceNumber, placeOfRegister);
    }
}
