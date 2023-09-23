package com.credibanco.Test.repository;

import com.credibanco.Test.model.dao.TransactionDao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TransactionRepository extends JpaRepository<TransactionDao, Long> {
    Optional<TransactionDao> findByTransactionIdAndStatusTransactionDaoStatus(String transactionId, String status);

    Optional<TransactionDao> findByTransactionId(String transactionId);
}
