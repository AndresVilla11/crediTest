package com.credibanco.Test.repository;

import com.credibanco.Test.model.dao.ProductTypeDao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProductTypeRepository extends JpaRepository<ProductTypeDao, Long> {
    Optional<ProductTypeDao> findByCode(String code);
}
