package com.credibanco.Test.util;

import com.credibanco.Test.jwt.JwtService;
import com.credibanco.Test.repository.CardRepository;
import com.credibanco.Test.repository.TransactionRepository;
import jakarta.servlet.http.HttpServletRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpHeaders;

import java.util.Optional;

import static com.credibanco.Test.ObjectsMock.cardDaoComplete;
import static com.credibanco.Test.ObjectsMock.token;
import static com.credibanco.Test.ObjectsMock.transactionDaoComplete;
import static com.credibanco.Test.util.Constant.PREFIX;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

class UtilsTest {
    @Mock
    CardRepository cardRepository;
    @Mock
    TransactionRepository transactionRepository;
    @Mock
    JwtService jwtService;
    @Mock
    HttpServletRequest request;
    @InjectMocks
    Utils utils;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getTokenFromRequest() {

        when(request.getHeader(HttpHeaders.AUTHORIZATION)).thenReturn(PREFIX + token());
        when(jwtService.getUserNameFromToken(anyString())).thenReturn(token());

        String tokenFromRequest = utils.getTokenFromRequest(request);

        assertNotNull(tokenFromRequest);

    }

    @Test
    void getTokenFromRequestNull() {

        String tokenFromRequest = utils.getTokenFromRequest(request);

        assertNull(tokenFromRequest);

    }

    @Test
    void findNewCardNumber() {

        when(cardRepository.findByNumberCard(anyLong())).thenReturn(Optional.empty());

        Long newCardNumber = utils.findNewCardNumber(anyString());

        assertNotNull(newCardNumber);
    }

    @Test
    void findNewCardNumberException() {

        when(cardRepository.findByNumberCard(anyLong())).thenReturn(Optional.of(cardDaoComplete()));

        Exception exception = assertThrows(RuntimeException.class, () -> {
            utils.findNewCardNumber(anyString());
        });

        assertEquals("Contact admin", exception.getMessage());
    }

    @Test
    void findNewTransactionId() {

        when(transactionRepository.findByTransactionId(anyString())).thenReturn(Optional.empty());

        Long newTransactionId = utils.findNewTransactionId();

        assertNotNull(newTransactionId);

    }

    @Test
    void findNewTransactionIdException() {

        when(transactionRepository.findByTransactionId(anyString())).thenReturn(Optional.of(transactionDaoComplete()));

        Exception exception = assertThrows(RuntimeException.class, () -> {
            utils.findNewTransactionId();
        });

        assertEquals("Contact admin", exception.getMessage());

    }
}