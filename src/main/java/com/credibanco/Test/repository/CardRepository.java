package com.credibanco.Test.repository;

import com.credibanco.Test.model.dao.CardDao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CardRepository extends JpaRepository<CardDao, Long> {
    Optional<CardDao> findByNumberCard(Long numberCard);
}
