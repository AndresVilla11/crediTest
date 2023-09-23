package com.credibanco.Test.controller;

import com.credibanco.Test.model.dto.BalanceDto;
import com.credibanco.Test.model.dto.CardDto;
import com.credibanco.Test.model.dto.CardRechargeDto;
import com.credibanco.Test.model.dto.ProductDto;
import com.credibanco.Test.service.CardService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/card")
public class CardController {

    private final CardService cardService;

    @GetMapping(value = "/{productId}/number")
    @ResponseStatus(HttpStatus.CREATED)
    private CardDto generateCard(HttpServletRequest request, @Valid @ModelAttribute("productId") ProductDto productId) {
        return cardService.generateCardNumber(request, productId);
    }

    @PostMapping(value = "/enroll")
    @ResponseStatus(HttpStatus.CREATED)
    private void enrollCard(@Valid @RequestBody CardDto cardDto) {
        cardService.enrollCard(cardDto);
    }

    @DeleteMapping(value = "/{cardId}")
    @ResponseStatus(HttpStatus.OK)
    private void blockCard(@Valid @ModelAttribute("cardId") CardDto cardDto) {
        cardService.blockCard(cardDto);
    }

    @PostMapping(value = "/balance")
    @ResponseStatus(HttpStatus.OK)
    private void rechargeCredit(@Valid @RequestBody CardRechargeDto cardRechargeDto) {
        cardService.rechargeCredit(cardRechargeDto);
    }

    @GetMapping(value = "/balance/{cardId}")
    @ResponseStatus(HttpStatus.OK)
    private BalanceDto getBalance(@Valid @ModelAttribute("cardId") CardDto cardDto) {
        return cardService.getBalance(cardDto);
    }
}
