package com.credibanco.Test.service;

import com.credibanco.Test.model.dto.PurchaseDto;
import com.credibanco.Test.model.dto.TransactionDetailsDto;
import com.credibanco.Test.model.dto.TransactionDto;

public interface TransactionService {
    TransactionDto purchaseCard(PurchaseDto purchaseDto);

    void anulationPurchaseCard(TransactionDto transactionDto);
    TransactionDetailsDto getTransactionId(String transactionId);
}
