package com.credibanco.Test.model.dao;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Date;


@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "transactions")
public class TransactionDao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "transaction_id")
    private String transactionId;

    @Column(name = "amount")
    private BigDecimal amountTransaction;

    @Column(name = "creation_date_time")
    @Temporal(TemporalType.TIMESTAMP)
    private Date creationDateTime;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "status_transaction", referencedColumnName = "id")
    private StatusTransactionDao statusTransactionDao;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "card_id", referencedColumnName = "id")
    private CardDao cardDaoSet;
}
