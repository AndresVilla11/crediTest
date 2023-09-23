package com.credibanco.Test.model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.math.BigDecimal;

import static com.credibanco.Test.util.Constant.ERROR_AMOUNT_DTO;
import static com.credibanco.Test.util.Constant.ERROR_DTO;

@EqualsAndHashCode(callSuper = true)
@Data
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
public class PurchaseDto extends CardDto {

    @NotNull(message = "Price" + ERROR_DTO)
    @DecimalMin(value = "0", inclusive = false, message = "Price" + ERROR_AMOUNT_DTO)
    @JsonProperty(value = "price")
    private BigDecimal price;
}
