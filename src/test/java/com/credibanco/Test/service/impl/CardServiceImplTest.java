package com.credibanco.Test.service.impl;

import com.credibanco.Test.exceptions.DataBaseException;
import com.credibanco.Test.exceptions.NotFoundException;
import com.credibanco.Test.model.dao.CardDao;
import com.credibanco.Test.model.dto.BalanceDto;
import com.credibanco.Test.model.dto.CardDto;
import com.credibanco.Test.repository.CardRepository;
import com.credibanco.Test.repository.ProductTypeRepository;
import com.credibanco.Test.repository.StatusRepository;
import com.credibanco.Test.repository.UserRepository;
import com.credibanco.Test.util.Utils;
import jakarta.servlet.http.HttpServletRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpHeaders;

import java.util.Optional;

import static com.credibanco.Test.ObjectsMock.cardDaoComplete;
import static com.credibanco.Test.ObjectsMock.cardDtoComplete;
import static com.credibanco.Test.ObjectsMock.cardRechargeDtoComplete;
import static com.credibanco.Test.ObjectsMock.newNumberCard;
import static com.credibanco.Test.ObjectsMock.productDtoComplete;
import static com.credibanco.Test.ObjectsMock.productTypeDaoComplete;
import static com.credibanco.Test.ObjectsMock.statusDaoActiveComplete;
import static com.credibanco.Test.ObjectsMock.statusDaoBlockComplete;
import static com.credibanco.Test.ObjectsMock.statusDaoInactiveComplete;
import static com.credibanco.Test.ObjectsMock.token;
import static com.credibanco.Test.ObjectsMock.userDaoComplete;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class CardServiceImplTest {

    @Mock
    UserRepository userRepository;
    @Mock
    ProductTypeRepository productTypeRepository;
    @Mock
    StatusRepository statusRepository;
    @Mock
    CardRepository cardRepository;
    @Mock
    Utils utils;
    @Mock
    HttpServletRequest request;
    @InjectMocks
    CardServiceImpl cardServiceImpl;

    @BeforeEach
    public void createMocks() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void generateCardNumber() {

        when(request.getHeader(HttpHeaders.AUTHORIZATION)).thenReturn("Bearer " + token());
        when(utils.getTokenFromRequest(request)).thenReturn(token());
        when(utils.findNewCardNumber(anyString())).thenReturn(newNumberCard());

        when(userRepository.findByUsername(anyString())).thenReturn(userDaoComplete());
        when(productTypeRepository.findByCode(anyString())).thenReturn(productTypeDaoComplete());
        when(statusRepository.findByStatus(anyString())).thenReturn(statusDaoInactiveComplete());
        when(cardRepository.save(any(CardDao.class))).thenReturn(cardDaoComplete());

        CardDto cardDto = cardServiceImpl.generateCardNumber(request, productDtoComplete());

        assertEquals("1010103577751633", cardDto.getCardId());
    }

    @Test
    void generateCardNumberUserDontExitException() {

        when(request.getHeader(HttpHeaders.AUTHORIZATION)).thenReturn("Bearer " + token());
        when(utils.getTokenFromRequest(request)).thenReturn(token());
        when(utils.findNewCardNumber(anyString())).thenReturn(newNumberCard());

        Exception exception = assertThrows(NotFoundException.class, () -> {
            cardServiceImpl.generateCardNumber(request, productDtoComplete());
        });
        assertEquals("Not Found: Doesn't exist user", exception.getMessage());
    }

    @Test
    void generateCardNumberProductDontExitException() {

        when(request.getHeader(HttpHeaders.AUTHORIZATION)).thenReturn("Bearer " + token());
        when(utils.getTokenFromRequest(request)).thenReturn(token());
        when(utils.findNewCardNumber(anyString())).thenReturn(newNumberCard());

        when(userRepository.findByUsername(anyString())).thenReturn(userDaoComplete());

        Exception exception = assertThrows(NotFoundException.class, () -> {
            cardServiceImpl.generateCardNumber(request, productDtoComplete());
        });
        assertEquals("Not Found: Doesn't exist product", exception.getMessage());
    }

    @Test
    void generateCardNumberStatusDontExitException() {

        when(request.getHeader(HttpHeaders.AUTHORIZATION)).thenReturn("Bearer " + token());
        when(utils.getTokenFromRequest(request)).thenReturn(token());
        when(utils.findNewCardNumber(anyString())).thenReturn(newNumberCard());

        when(userRepository.findByUsername(anyString())).thenReturn(userDaoComplete());
        when(productTypeRepository.findByCode(anyString())).thenReturn(productTypeDaoComplete());

        Exception exception = assertThrows(NotFoundException.class, () -> {
            cardServiceImpl.generateCardNumber(request, productDtoComplete());
        });
        assertEquals("Not Found: Doesn't exist status", exception.getMessage());
    }

    @Test
    void generateCardNumberSaveException() {

        when(request.getHeader(HttpHeaders.AUTHORIZATION)).thenReturn("Bearer " + token());
        when(utils.getTokenFromRequest(request)).thenReturn(token());
        when(utils.findNewCardNumber(anyString())).thenReturn(newNumberCard());

        when(userRepository.findByUsername(anyString())).thenReturn(userDaoComplete());
        when(productTypeRepository.findByCode(anyString())).thenReturn(productTypeDaoComplete());
        when(statusRepository.findByStatus(anyString())).thenReturn(statusDaoInactiveComplete());

        Exception exception = assertThrows(DataBaseException.class, () -> {
            cardServiceImpl.generateCardNumber(request, productDtoComplete());
        });
        assertNotNull(exception.getMessage());
    }

    @Test
    void enrollCard() {

        when(cardRepository.findByNumberCard(anyLong())).thenReturn(Optional.of(cardDaoComplete()));
        when(statusRepository.findByStatus(anyString())).thenReturn(statusDaoActiveComplete());

        cardServiceImpl.enrollCard(cardDtoComplete());

        verify(cardRepository).save(any(CardDao.class));

    }

    @Test
    void enrollCardException() {

        when(cardRepository.findByNumberCard(anyLong())).thenReturn(Optional.of(cardDaoComplete()));
        when(statusRepository.findByStatus(anyString())).thenReturn(statusDaoActiveComplete());

        when(cardRepository.save(any(CardDao.class))).thenThrow(DataBaseException.class);

        Exception exception = assertThrows(DataBaseException.class, () -> {
            cardServiceImpl.enrollCard(cardDtoComplete());
        });
        assertEquals("Error saving information ", exception.getMessage());
    }

    @Test
    void enrollCardIdException() {

        Exception exception = assertThrows(NotFoundException.class, () -> {
            cardServiceImpl.enrollCard(cardDtoComplete());
        });
        assertEquals("Not Found: Doesn't exist card number", exception.getMessage());
    }

    @Test
    void blockCard() {
        when(cardRepository.findByNumberCard(anyLong())).thenReturn(Optional.of(cardDaoComplete()));
        when(statusRepository.findByStatus(anyString())).thenReturn(statusDaoBlockComplete());

        cardServiceImpl.blockCard(cardDtoComplete());

        verify(cardRepository).save(any(CardDao.class));
    }

    @Test
    void blockCardException() {

        when(cardRepository.findByNumberCard(anyLong())).thenReturn(Optional.of(cardDaoComplete()));
        when(statusRepository.findByStatus(anyString())).thenReturn(statusDaoActiveComplete());

        when(cardRepository.save(any(CardDao.class))).thenThrow(DataBaseException.class);

        Exception exception = assertThrows(DataBaseException.class, () -> {
            cardServiceImpl.blockCard(cardDtoComplete());
        });
        assertEquals("Error saving information ", exception.getMessage());
    }

    @Test
    void rechargeCredit() {

        when(cardRepository.findByNumberCard(anyLong())).thenReturn(Optional.of(cardDaoComplete()));

        cardServiceImpl.rechargeCredit(cardRechargeDtoComplete());

        verify(cardRepository).save(any(CardDao.class));

    }

    @Test
    void rechargeCreditException() {

        when(cardRepository.findByNumberCard(anyLong())).thenReturn(Optional.of(cardDaoComplete()));

        when(cardRepository.save(any(CardDao.class))).thenThrow(DataBaseException.class);

        Exception exception = assertThrows(DataBaseException.class, () -> {
            cardServiceImpl.rechargeCredit(cardRechargeDtoComplete());
        });

        assertEquals("Error saving information ", exception.getMessage());
    }

    @Test
    void getBalance() {

        when(cardRepository.findByNumberCard(anyLong())).thenReturn(Optional.of(cardDaoComplete()));

        BalanceDto balance = cardServiceImpl.getBalance(cardDtoComplete());

        assertEquals("0", balance.getBalance());
    }

}