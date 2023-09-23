package com.credibanco.Test.repository;

import com.credibanco.Test.model.dao.StatusTransactionDao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface StatusTransactionRepository extends JpaRepository<StatusTransactionDao, Long> {
    Optional<StatusTransactionDao> findByStatus(String status);
}
