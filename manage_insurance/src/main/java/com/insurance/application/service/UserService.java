package com.insurance.application.service;

import com.insurance.application.form.InsuranceForm;

import java.util.List;

public interface UserService {
    InsuranceForm findById(int id);

    InsuranceForm findByUsername(String username);

    InsuranceForm findByInsuNumberAndFullName(String insuNum, String fullName);

    boolean insert(InsuranceForm registerForm) throws Exception;

    boolean update(InsuranceForm registerForm, int id) throws Exception;

    void deleteById(int id);

    List<InsuranceForm> getListUser(int companyId, String name, String insuranceNumber,
                                    String placeOfRegister, String sortType, int page);

    int getTotalUser(int companyId, String name, String insuranceNumber, String placeOfRegister);
}
