package com.credibanco.Test.service.impl;

import com.credibanco.Test.exceptions.DataBaseException;
import com.credibanco.Test.exceptions.NotFoundException;
import com.credibanco.Test.model.dao.TransactionDao;
import com.credibanco.Test.model.dto.TransactionDetailsDto;
import com.credibanco.Test.model.dto.TransactionDto;
import com.credibanco.Test.repository.CardRepository;
import com.credibanco.Test.repository.StatusRepository;
import com.credibanco.Test.repository.StatusTransactionRepository;
import com.credibanco.Test.repository.TransactionRepository;
import com.credibanco.Test.util.Utils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static com.credibanco.Test.ObjectsMock.StatusTransactionDaoApproveComplete;
import static com.credibanco.Test.ObjectsMock.cardDaoWithAmountAndDateWrongComplete;
import static com.credibanco.Test.ObjectsMock.cardDaoWithAmountComplete;
import static com.credibanco.Test.ObjectsMock.purchaseBigPriceDtoComplete;
import static com.credibanco.Test.ObjectsMock.purchaseDtoComplete;
import static com.credibanco.Test.ObjectsMock.statusDaoActiveComplete;
import static com.credibanco.Test.ObjectsMock.statusDaoInactiveComplete;
import static com.credibanco.Test.ObjectsMock.transactionDaoComplete;
import static com.credibanco.Test.ObjectsMock.transactionDtoComplete;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class TransactionServiceImplTest {
    @Mock
    CardRepository cardRepository;
    @Mock
    StatusRepository statusRepository;
    @Mock
    StatusTransactionRepository statusTransactionRepository;
    @Mock
    TransactionRepository transactionRepository;
    @Mock
    Utils utils;
    @InjectMocks
    TransactionServiceImpl transactionServiceImpl;

    @BeforeEach
    public void createMocks() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void purchaseCard() {

        when(cardRepository.findByNumberCard(anyLong())).thenReturn(Optional.of(cardDaoWithAmountComplete()));
        when(statusRepository.findByStatus(anyString())).thenReturn(statusDaoActiveComplete());
        when(statusTransactionRepository.findByStatus(anyString())).thenReturn(StatusTransactionDaoApproveComplete());
        when(transactionRepository.save(any(TransactionDao.class))).thenReturn(transactionDaoComplete());

        TransactionDto transactionDto = transactionServiceImpl.purchaseCard(purchaseDtoComplete());

        assertEquals("10101010101", transactionDto.getTransactionId());

    }

    @Test
    void purchaseCardAmountDenied() {

        when(cardRepository.findByNumberCard(anyLong())).thenReturn(Optional.of(cardDaoWithAmountComplete()));
        when(statusRepository.findByStatus(anyString())).thenReturn(statusDaoActiveComplete());
        when(statusTransactionRepository.findByStatus(anyString())).thenReturn(StatusTransactionDaoApproveComplete());
        when(transactionRepository.save(any(TransactionDao.class))).thenReturn(transactionDaoComplete());

        TransactionDto transactionDto = transactionServiceImpl.purchaseCard(purchaseBigPriceDtoComplete());

        assertEquals("10101010101", transactionDto.getTransactionId());

    }

    @Test
    void purchaseCardDeniedForDate() {

        when(cardRepository.findByNumberCard(anyLong())).thenReturn(Optional.of(cardDaoWithAmountAndDateWrongComplete()));
        when(statusRepository.findByStatus(anyString())).thenReturn(statusDaoActiveComplete());
        when(statusTransactionRepository.findByStatus(anyString())).thenReturn(StatusTransactionDaoApproveComplete());
        when(transactionRepository.save(any(TransactionDao.class))).thenReturn(transactionDaoComplete());

        TransactionDto transactionDto = transactionServiceImpl.purchaseCard(purchaseDtoComplete());

        assertEquals("10101010101", transactionDto.getTransactionId());

    }

    @Test
    void purchaseFindCardException() {

        Exception exception = assertThrows(NotFoundException.class, () -> {
            transactionServiceImpl.purchaseCard(purchaseDtoComplete());
        });

        assertEquals("Not Found: Doesn't exist card number", exception.getMessage());

    }

    @Test
    void purchaseCardStatusException() {

        when(cardRepository.findByNumberCard(anyLong())).thenReturn(Optional.of(cardDaoWithAmountComplete()));

        Exception exception = assertThrows(NotFoundException.class, () -> {
            transactionServiceImpl.purchaseCard(purchaseDtoComplete());
        });

        assertEquals("Not Found: Doesn't exist status", exception.getMessage());

    }

    @Test
    void annulationTransactionStatusException() {

        when(cardRepository.findByNumberCard(anyLong())).thenReturn(Optional.of(cardDaoWithAmountComplete()));
        when(statusRepository.findByStatus(anyString())).thenReturn(statusDaoActiveComplete());
        when(statusTransactionRepository.findByStatus(anyString())).thenReturn(Optional.empty());

        NotFoundException notFoundException = assertThrows(NotFoundException.class, () -> {
            transactionServiceImpl.purchaseCard(purchaseDtoComplete());
        });

        assertEquals("Not Found: Doesn't exist status", notFoundException.getMessage());
    }

    @Test
    void purchaseCardDenied() {

        when(cardRepository.findByNumberCard(anyLong())).thenReturn(Optional.of(cardDaoWithAmountComplete()));
        when(statusRepository.findByStatus(anyString())).thenReturn(statusDaoInactiveComplete());
        when(statusTransactionRepository.findByStatus(anyString())).thenReturn(StatusTransactionDaoApproveComplete());
        when(transactionRepository.save(any(TransactionDao.class))).thenReturn(transactionDaoComplete());

        TransactionDto transactionDto = transactionServiceImpl.purchaseCard(purchaseDtoComplete());

        assertEquals("10101010101", transactionDto.getTransactionId());

    }

    @Test
    void purchaseCardException() {

        when(cardRepository.findByNumberCard(anyLong())).thenReturn(Optional.of(cardDaoWithAmountComplete()));
        when(statusRepository.findByStatus(anyString())).thenReturn(statusDaoInactiveComplete());
        when(statusTransactionRepository.findByStatus(anyString())).thenReturn(StatusTransactionDaoApproveComplete());
        when(transactionRepository.save(any(TransactionDao.class))).thenThrow(DataBaseException.class);

        Exception exception = assertThrows(DataBaseException.class, () -> {
            transactionServiceImpl.purchaseCard(purchaseDtoComplete());
        });

        assertEquals("Error saving information ", exception.getMessage());

    }

    @Test
    void anulationPurchaseCard() {

        when(cardRepository.findByNumberCard(anyLong())).thenReturn(Optional.of(cardDaoWithAmountComplete()));
        when(statusRepository.findByStatus(anyString())).thenReturn(statusDaoActiveComplete());
        when(transactionRepository.findByTransactionIdAndStatusTransactionDaoStatus(anyString(), anyString())).thenReturn(Optional.of(transactionDaoComplete()));
        when(statusTransactionRepository.findByStatus(anyString())).thenReturn(StatusTransactionDaoApproveComplete());
        when(transactionRepository.save(any(TransactionDao.class))).thenReturn(transactionDaoComplete());

        transactionServiceImpl.anulationPurchaseCard(transactionDtoComplete());

        verify(transactionRepository).save(any(TransactionDao.class));
    }

    @Test
    void anulationPurchaseCardException() {

        when(cardRepository.findByNumberCard(anyLong())).thenReturn(Optional.of(cardDaoWithAmountComplete()));
        when(statusRepository.findByStatus(anyString())).thenReturn(statusDaoActiveComplete());
        when(transactionRepository.findByTransactionIdAndStatusTransactionDaoStatus(anyString(), anyString())).thenReturn(Optional.of(transactionDaoComplete()));
        when(statusTransactionRepository.findByStatus(anyString())).thenReturn(StatusTransactionDaoApproveComplete());
        when(transactionRepository.save(any(TransactionDao.class))).thenThrow(DataBaseException.class);

        Exception exception = assertThrows(DataBaseException.class, () -> {
            transactionServiceImpl.anulationPurchaseCard(transactionDtoComplete());
        });
        assertEquals("Error saving information ", exception.getMessage());
    }

    @Test
    void getTransactionId() {
        when(transactionRepository.findByTransactionId(anyString())).thenReturn(Optional.of(transactionDaoComplete()));

        TransactionDetailsDto transactionId = transactionServiceImpl.getTransactionId(anyString());

        assertEquals("10101010101", transactionId.getTransactionId());
    }
}