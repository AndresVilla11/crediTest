package com.credibanco.Test.controller;

import com.credibanco.Test.model.dto.CardDto;
import com.credibanco.Test.model.dto.PurchaseDto;
import com.credibanco.Test.model.dto.TransactionDetailsDto;
import com.credibanco.Test.model.dto.TransactionDto;
import com.credibanco.Test.service.TransactionService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/transaction")
public class TransactionController {

    private final TransactionService transactionService;

    @PostMapping(value = "/purchase")
    @ResponseStatus(HttpStatus.OK)
    private TransactionDto purchaseCard(@Valid @RequestBody PurchaseDto purchaseDto) {
        return transactionService.purchaseCard(purchaseDto);
    }

    @PostMapping(value = "/anulation")
    @ResponseStatus(HttpStatus.OK)
    private void anulationPurchaseCard(@Valid @RequestBody TransactionDto transactionDto) {
        transactionService.anulationPurchaseCard(transactionDto);
    }

    @GetMapping(value = "/{transactionId}")
    @ResponseStatus(HttpStatus.OK)
    private TransactionDetailsDto getTransaction(@ModelAttribute("transactionId") String transactionId) {
        return transactionService.getTransactionId(transactionId);
    }
}
