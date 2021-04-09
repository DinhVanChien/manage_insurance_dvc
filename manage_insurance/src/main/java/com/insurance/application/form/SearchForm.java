package com.insurance.application.form;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class SearchForm {
    private String userFullName;
    private String insuranceNumber;
    private String placeRegister;
    private int companyId;
    private int page;
    private String sortName;
    private String sortInNumber;
    private String sortCreateDate;
}
