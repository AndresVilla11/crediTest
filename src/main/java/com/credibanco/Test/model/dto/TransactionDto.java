package com.credibanco.Test.model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import static com.credibanco.Test.util.Constant.ERROR_DTO;

@EqualsAndHashCode(callSuper = true)
@Data
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
public class TransactionDto extends CardDto {
    @NotBlank(message = "Transaction id" + ERROR_DTO)
    @JsonProperty(value = "transactionId")
    private String transactionId;
}
