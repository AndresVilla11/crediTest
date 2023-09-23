package com.credibanco.Test.service.impl;

import com.credibanco.Test.model.dao.CardDao;
import com.credibanco.Test.model.dao.StatusDao;
import com.credibanco.Test.model.dao.StatusTransactionDao;
import com.credibanco.Test.model.dao.TransactionDao;
import com.credibanco.Test.model.dto.PurchaseDto;
import com.credibanco.Test.model.dto.TransactionDetailsDto;
import com.credibanco.Test.model.dto.TransactionDto;
import com.credibanco.Test.repository.CardRepository;
import com.credibanco.Test.repository.StatusRepository;
import com.credibanco.Test.repository.StatusTransactionRepository;
import com.credibanco.Test.repository.TransactionRepository;
import com.credibanco.Test.service.TransactionService;
import com.credibanco.Test.util.Utils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Date;

import static com.credibanco.Test.util.Constant.ACTIVO;
import static com.credibanco.Test.util.Constant.ANULADA;
import static com.credibanco.Test.util.Constant.APROBADO;
import static com.credibanco.Test.util.Constant.NEGADO;

@Service
@RequiredArgsConstructor
public class TransactionServiceImpl implements TransactionService {

    private final CardRepository cardRepository;
    private final StatusRepository statusRepository;
    private final StatusTransactionRepository statusTransactionRepository;
    private final TransactionRepository transactionRepository;

    private final Utils utils;

    @Override
    public TransactionDto purchaseCard(PurchaseDto purchaseDto) {

        CardDao cardDao = findCard(purchaseDto.getCardId());
        StatusDao statusDao = statusRepository.findByStatus(ACTIVO)
                .orElseThrow(() -> new RuntimeException("Doesn't exist status"));

        try {
            boolean isTransactionDenied = (!(cardDao.getStatus() == statusDao)
                    || (cardDao.getExpirationDate().isBefore(LocalDate.now()))
                    || (purchaseDto.getPrice().compareTo(cardDao.getAmount()) >= 0));

            BigDecimal transactionAmount = purchaseDto.getPrice();
            String transactionStatus = isTransactionDenied ? NEGADO : APROBADO;

            if (!isTransactionDenied) {
                cardDao.setAmount(cardDao.getAmount().subtract(transactionAmount));
            }

            TransactionDao transactionDao = getTransactionDao(cardDao, transactionStatus, transactionAmount);
            TransactionDao savedTransaction = transactionRepository.save(transactionDao);

            return getTransactionDto(purchaseDto, savedTransaction);
        } catch (Exception exception) {
            throw new RuntimeException(exception.getMessage());
        }
    }

    @Override
    public void anulationPurchaseCard(TransactionDto transactionDto) {
        CardDao cardDao = findCard(transactionDto.getCardId());
        StatusDao statusDao = statusRepository.findByStatus(ACTIVO)
                .orElseThrow(() -> new RuntimeException("Doesn't exist status"));
        TransactionDao transactionDaoActive = transactionRepository.findByTransactionIdAndStatusTransactionDaoStatus(transactionDto.getTransactionId(), APROBADO)
                .orElseThrow(() -> new RuntimeException("Doesn't exist transaction"));
        try {
            boolean isTransactionVoidable = (!(cardDao.getStatus() == statusDao)
                    || (cardDao.getExpirationDate().isBefore(LocalDate.now()))
                    || (isDateCompareForAnnulation(transactionDaoActive.getCreationDateTime())));

            BigDecimal transactionAmount = transactionDaoActive.getAmountTransaction();
            String transactionStatus = isTransactionVoidable ? APROBADO : ANULADA;

            if (!isTransactionVoidable) {
                cardDao.setAmount(cardDao.getAmount().add(transactionAmount));
            }
            transactionDaoActive.setStatusTransactionDao(getStatusTransactionDao(transactionStatus));
            transactionRepository.save(transactionDaoActive);

        } catch (Exception exception) {
            throw new RuntimeException(exception.getMessage());
        }
    }

    @Override
    public TransactionDetailsDto getTransactionId(String transactionId) {
        TransactionDao transactionDao = transactionRepository.findByTransactionId(transactionId)
                .orElseThrow(() -> new RuntimeException("Doesn't exist transaction"));
        return TransactionDetailsDto.builder()
                .cardId(String.valueOf(transactionDao.getCardDaoSet().getNumberCard()))
                .transactionId(transactionDao.getTransactionId())
                .price(transactionDao.getAmountTransaction())
                .build();
    }

    private boolean isDateCompareForAnnulation(Date creationDateTime) {
        Date dateAfter = new Date();
        long differenceTime = (dateAfter.getTime() - creationDateTime.getTime()) / (60 * 60 * 1000);
        return differenceTime >= 24;
    }

    private static TransactionDto getTransactionDto(PurchaseDto purchaseDto, TransactionDao savedTransactional) {
        return TransactionDto.builder()
                .cardId(purchaseDto.getCardId())
                .transactionId(savedTransactional.getTransactionId())
                .build();
    }

    private TransactionDao getTransactionDao(CardDao cardDao, String status, BigDecimal price) {
        return TransactionDao.builder()
                .transactionId(String.valueOf(utils.findNewTransactionId()))
                .amountTransaction(price)
                .creationDateTime(new Date())
                .statusTransactionDao(getStatusTransactionDao(status))
                .cardDaoSet(cardDao)
                .build();
    }

    private StatusTransactionDao getStatusTransactionDao(String status) {
        return statusTransactionRepository.findByStatus(status)
                .orElseThrow(() -> new RuntimeException("Doesn't exist status"));
    }

    private CardDao findCard(String cardId) {
        return cardRepository.findByNumberCard(Long.valueOf(cardId))
                .orElseThrow(() -> new RuntimeException("Doesn't exist card number"));
    }
}
