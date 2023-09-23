package com.credibanco.Test.model.dao;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Digits;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Set;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "cards")
public class CardDao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 16, name = "number_card")
    @Digits(integer = 16, fraction = 0)
    private Long numberCard;

    @Column(name = "expiration_date")
    private LocalDate expirationDate;

    @Column(name = "amount")
    @DecimalMin(value = "0.00")
    private BigDecimal amount;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "cardDaoSet", cascade = CascadeType.ALL)
    private Set<TransactionDao> transactionDaoSet;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_user", referencedColumnName = "id")
    private UserDao user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_type", referencedColumnName = "id")
    private ProductTypeDao productType;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "status", referencedColumnName = "id")
    private StatusDao status;
}
