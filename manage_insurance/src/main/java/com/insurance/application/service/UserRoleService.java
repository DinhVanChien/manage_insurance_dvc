package com.insurance.application.service;

import java.util.List;
import java.util.Set;

public interface UserRoleService {
    List<String> findRoleNamesByUserId(int id);

    Set<String> findRoleNamesByUserName(String username);
}
