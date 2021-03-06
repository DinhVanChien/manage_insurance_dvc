package com.insurance.application.service.impl;

import com.insurance.application.repository.UserRepository;
import com.insurance.application.repository.UserRoleRepository;
import com.insurance.application.security.CustomUserDetail;
import com.insurance.application.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    private UserRepository userRepository;
    private UserRoleRepository userRoleRepository;

    @Autowired
    public UserDetailsServiceImpl(UserRepository userRepository, UserRoleRepository userRoleRepository) {
        this.userRepository = userRepository;
        this.userRoleRepository = userRoleRepository;
    }

    // Spring Security sẽ cần lấy các thông tin UserDetails hiện có để kiểm tra
    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username);
        if (user != null) {
            List<String> roleName = userRoleRepository.findRoleNamesByUserId(user.getId());
            UserDetails userDetails = new CustomUserDetail(user, roleName);
            System.out.println(user.getUsername());
            System.out.println(user.getPassword());
            return userDetails;
        } else {
            throw new UsernameNotFoundException("User " + user.getUsername() + " was not found in the database");
        }
    }
}
