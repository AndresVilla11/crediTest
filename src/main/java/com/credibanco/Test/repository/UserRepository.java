package com.credibanco.Test.repository;

import com.credibanco.Test.model.dao.UserDao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<UserDao, Long> {
    Optional<UserDao> findByUsername(String userName);
}
