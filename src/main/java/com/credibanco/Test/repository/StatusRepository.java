package com.credibanco.Test.repository;

import com.credibanco.Test.model.dao.StatusDao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface StatusRepository extends JpaRepository<StatusDao, Long> {
    Optional<StatusDao> findByStatus(String status);
}
