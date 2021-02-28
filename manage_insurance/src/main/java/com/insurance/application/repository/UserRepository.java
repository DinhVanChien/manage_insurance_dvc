package com.insurance.application.repository;

import com.insurance.application.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
    User findByUsername(String username);

    User findById(int id);

    User save(User user);

    void deleteById(int id);

    @Query(value = "SELECT * FROM USERS u WHERE u.company_id = ?1", nativeQuery = true)
    List<User> findUsersByCompanyId(int companyId);

}
