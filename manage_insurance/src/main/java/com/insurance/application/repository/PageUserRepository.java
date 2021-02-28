package com.insurance.application.repository;

import com.insurance.application.entity.User;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional
public interface PageUserRepository extends PagingAndSortingRepository<User, Integer> {
}
