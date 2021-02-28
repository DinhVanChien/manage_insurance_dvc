package com.insurance.application.repository;

import com.insurance.application.entity.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;

@Repository
@Transactional
public interface UserRoleRepository extends JpaRepository<UserRole, Integer> {
    @Query(value = "SELECT r.role_name FROM ROLE r JOIN USER_ROLE u " +
            "ON r.role_id = u.role_id " +
            "WHERE u.user_id = ?1",
            nativeQuery = true)
    List<String> findRoleNamesByUserId(int id);

    @Query(value = "SELECT r.role_name FROM ROLE r JOIN USER_ROLE u " +
            "ON r.role_id = u.role_id JOIN USERS us " +
            "ON us.id = u.user_id " +
            "WHERE us.username = ?1",
            nativeQuery = true)
    Set<String> findRoleNamesByUserName(String username);
}
