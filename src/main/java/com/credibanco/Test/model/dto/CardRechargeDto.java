package com.credibanco.Test.model.dto;

import com.credibanco.Test.validator.GreaterThanZero;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@EqualsAndHashCode(callSuper = true)
@Data
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
public class CardRechargeDto extends CardDto {

    @NotBlank
    @JsonProperty(value = "balance")
    @GreaterThanZero
    private String balance;

}
