package com.insurance.application.service.impl;

import com.insurance.application.repository.UserRoleRepository;
import com.insurance.application.service.UserRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;

@Service
@Transactional
public class UserRoleServiceImpl implements UserRoleService {

    private UserRoleRepository roleUserDao;

    @Autowired
    public UserRoleServiceImpl(UserRoleRepository roleUserDao) {
        this.roleUserDao = roleUserDao;
    }

    @Override
    public List<String> findRoleNamesByUserId(int id) {
        return roleUserDao.findRoleNamesByUserId(id);
    }

    @Override
    public Set<String> findRoleNamesByUserName(String username) {
        return roleUserDao.findRoleNamesByUserName(username);
    }
}
