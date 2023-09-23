package com.credibanco.Test.service;

import com.credibanco.Test.model.dto.BalanceDto;
import com.credibanco.Test.model.dto.CardDto;
import com.credibanco.Test.model.dto.CardRechargeDto;
import com.credibanco.Test.model.dto.ProductDto;
import jakarta.servlet.http.HttpServletRequest;

public interface CardService {
    CardDto generateCardNumber(HttpServletRequest request, ProductDto productDto);

    void enrollCard(CardDto cardDto);

    void blockCard(CardDto cardDto);

    void rechargeCredit(CardRechargeDto cardRechargeDto);

    BalanceDto getBalance(CardDto cardDto);
}
