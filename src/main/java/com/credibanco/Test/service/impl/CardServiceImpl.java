package com.credibanco.Test.service.impl;

import com.credibanco.Test.exceptions.DataBaseException;
import com.credibanco.Test.exceptions.NotFoundException;
import com.credibanco.Test.model.dao.CardDao;
import com.credibanco.Test.model.dao.ProductTypeDao;
import com.credibanco.Test.model.dao.StatusDao;
import com.credibanco.Test.model.dao.UserDao;
import com.credibanco.Test.model.dto.BalanceDto;
import com.credibanco.Test.model.dto.CardDto;
import com.credibanco.Test.model.dto.CardRechargeDto;
import com.credibanco.Test.model.dto.ProductDto;
import com.credibanco.Test.repository.CardRepository;
import com.credibanco.Test.repository.ProductTypeRepository;
import com.credibanco.Test.repository.StatusRepository;
import com.credibanco.Test.repository.UserRepository;
import com.credibanco.Test.service.CardService;
import com.credibanco.Test.util.Utils;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;

import static com.credibanco.Test.util.Constant.ACTIVO;
import static com.credibanco.Test.util.Constant.BLOQUEAR;
import static com.credibanco.Test.util.Constant.ERROR_SAVE;
import static com.credibanco.Test.util.Constant.INACTIVO;

@Service
@RequiredArgsConstructor
public class CardServiceImpl implements CardService {

    private final UserRepository userRepository;
    private final ProductTypeRepository productTypeRepository;
    private final StatusRepository statusRepository;
    private final CardRepository cardRepository;

    private final Utils utils;

    @Override
    public CardDto generateCardNumber(HttpServletRequest request, ProductDto productDto) {

        String userNameFromToken = utils.getTokenFromRequest(request);

        UserDao user = userRepository.findByUsername(userNameFromToken)
                .orElseThrow(() -> new NotFoundException("Doesn't exist user"));
        ProductTypeDao productTypeDao = productTypeRepository.findByCode(productDto.getProductId())
                .orElseThrow(() -> new NotFoundException("Doesn't exist product"));
        StatusDao statusDao = getStatusDao(INACTIVO);

        Long newNumberCard = utils.findNewCardNumber(productDto.getProductId());

        CardDao cardDao = CardDao.builder()
                .numberCard(newNumberCard)
                .expirationDate(LocalDate.now().plusYears(3))
                .amount(new BigDecimal(0))
                .user(user)
                .productType(productTypeDao)
                .status(statusDao)
                .build();
        try {
            CardDao cardSaved = cardRepository.save(cardDao);
            return CardDto.builder()
                    .cardId(String.valueOf(cardSaved.getNumberCard()))
                    .build();
        } catch (Exception exception) {
            throw new DataBaseException(ERROR_SAVE);
        }
    }

    @Override
    public void enrollCard(CardDto cardDto) {
        CardDao cardDao = findCard(cardDto.getCardId());
        StatusDao statusDao = getStatusDao(ACTIVO);
        cardDao.setStatus(statusDao);
        try {
            cardRepository.save(cardDao);
        } catch (Exception exception) {
            throw new DataBaseException(ERROR_SAVE);
        }
    }

    @Override
    public void blockCard(CardDto cardDto) {
        CardDao cardDao = findCard(cardDto.getCardId());
        StatusDao statusDao = getStatusDao(BLOQUEAR);
        cardDao.setStatus(statusDao);
        try {
            cardRepository.save(cardDao);
        } catch (Exception exception) {
            throw new DataBaseException(ERROR_SAVE);
        }
    }

    @Override
    public void rechargeCredit(CardRechargeDto cardRechargeDto) {

        CardDao cardDao = findCard(cardRechargeDto.getCardId());

        BigDecimal sum = cardDao.getAmount().add(BigDecimal.valueOf(Long.parseLong(cardRechargeDto.getBalance())));
        cardDao.setAmount(sum);

        try {
            cardRepository.save(cardDao);
        } catch (Exception exception) {
            throw new DataBaseException(ERROR_SAVE);
        }
    }

    @Override
    public BalanceDto getBalance(CardDto cardDto) {
        CardDao cardDao = findCard(cardDto.getCardId());
        return BalanceDto.builder()
                .balance(String.valueOf(cardDao.getAmount()))
                .build();
    }

    private CardDao findCard(String cardId) {
        return cardRepository.findByNumberCard(Long.valueOf(cardId))
                .orElseThrow(() -> new NotFoundException("Doesn't exist card number"));
    }

    private StatusDao getStatusDao(String status) {
        return statusRepository.findByStatus(status)
                .orElseThrow(() -> new NotFoundException("Doesn't exist status"));
    }
}
