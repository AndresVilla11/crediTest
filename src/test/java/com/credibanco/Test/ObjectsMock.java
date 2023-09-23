package com.credibanco.Test;

import com.credibanco.Test.model.auth.Role;
import com.credibanco.Test.model.dao.CardDao;
import com.credibanco.Test.model.dao.ProductTypeDao;
import com.credibanco.Test.model.dao.StatusDao;
import com.credibanco.Test.model.dao.StatusTransactionDao;
import com.credibanco.Test.model.dao.TransactionDao;
import com.credibanco.Test.model.dao.UserDao;
import com.credibanco.Test.model.dto.CardDto;
import com.credibanco.Test.model.dto.CardRechargeDto;
import com.credibanco.Test.model.dto.ProductDto;
import com.credibanco.Test.model.dto.PurchaseDto;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Date;
import java.util.Optional;

import static com.credibanco.Test.util.Constant.ACTIVO;
import static com.credibanco.Test.util.Constant.APROBADO;
import static com.credibanco.Test.util.Constant.BLOQUEAR;
import static com.credibanco.Test.util.Constant.INACTIVO;

public class ObjectsMock {
    public static String token() {
        return "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJBViIsImlhdCI6MTY5NTQ5NTk2NiwiZXhwIjoxNjk1NDk2NTY2fQ.wfZYlSiXfwh7bSMK37ALFJnezvMECgCP4Hplvj-onoA";
    }

    public static Long newNumberCard() {
        return Long.valueOf("1010103577751633");
    }

    public static Optional<UserDao> userDaoComplete() {
        return Optional.of(UserDao.builder()
                .id(1L)
                .fullName("Full name")
                .identificationNumber("10101010101")
                .role(Role.USER)
                .username("User name")
                .build());
    }

    public static ProductDto productDtoComplete() {
        return ProductDto.builder()
                .productId("101010")
                .build();
    }

    public static Optional<ProductTypeDao> productTypeDaoComplete() {
        return Optional.of(ProductTypeDao.builder()
                .id(1L)
                .code("1")
                .type("activo")
                .build());
    }

    public static Optional<StatusDao> statusDaoInactiveComplete() {
        return Optional.of(StatusDao.builder()
                .id(1L)
                .status(INACTIVO)
                .build());
    }

    public static Optional<StatusDao> statusDaoActiveComplete() {
        return Optional.of(StatusDao.builder()
                .id(1L)
                .status(ACTIVO)
                .build());
    }

    public static Optional<StatusDao> statusDaoBlockComplete() {
        return Optional.of(StatusDao.builder()
                .id(1L)
                .status(BLOQUEAR)
                .build());
    }
    public static Optional<StatusTransactionDao> StatusTransactionDaoApproveComplete() {
        return Optional.of(StatusTransactionDao.builder()
                .id(1L)
                .status(APROBADO)
                .build());
    }

    public static CardDao cardDaoComplete() {
        return CardDao.builder()
                .id(1L)
                .numberCard(1010103577751633L)
                .expirationDate(LocalDate.now().plusYears(3))
                .amount(BigDecimal.ZERO)
                .status(statusDaoActiveComplete().get())
                .build();
    }
    public static TransactionDao transactionDaoComplete() {
        return TransactionDao.builder()
                .id(1L)
                .transactionId("10101010101")
                .amountTransaction(BigDecimal.TEN)
                .creationDateTime(new Date())
                .build();
    }

    public static CardDao cardDaoWithAmountComplete() {
        return CardDao.builder()
                .id(1L)
                .numberCard(1010103577751633L)
                .expirationDate(LocalDate.now().plusYears(3))
                .amount(BigDecimal.valueOf(1000L))
                .status(statusDaoActiveComplete().get())
                .build();
    }

    public static CardDto cardDtoComplete() {
        return CardDto.builder()
                .cardId("1010103577751633")
                .build();
    }

    public static CardRechargeDto cardRechargeDtoComplete() {
        return CardRechargeDto.builder()
                .cardId("1010103577751633")
                .balance("1000")
                .build();
    }

    public static PurchaseDto purchaseDtoComplete() {
        return PurchaseDto.builder()
                .cardId("1010103577751633")
                .price(BigDecimal.TEN)
                .build();
    }

}
