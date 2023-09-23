package com.credibanco.Test.model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import static com.credibanco.Test.util.Constant.ERROR_DTO;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BalanceDto {
    @NotBlank(message = "Balance" + ERROR_DTO)
    @JsonProperty(value = "balance")
    private String balance;
}
