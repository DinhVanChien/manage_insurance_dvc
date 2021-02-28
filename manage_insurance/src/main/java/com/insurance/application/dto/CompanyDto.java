package com.insurance.application.dto;

import com.insurance.application.entity.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CompanyDto {
    private int id;
    private String name;
    private String address;
    private String email;
    private String telephone;
    private List<User> users = new ArrayList<User>();
}