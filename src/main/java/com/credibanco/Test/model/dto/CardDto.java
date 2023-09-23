package com.credibanco.Test.model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import static com.credibanco.Test.util.Constant.DESIRED_LENGTH;
import static com.credibanco.Test.util.Constant.ERROR_DTO;
import static com.credibanco.Test.util.Constant.ERROR_LENGTH;

@Data
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
public class CardDto {

    @NotBlank(message = "Card id" + ERROR_DTO)
    @Size(min = DESIRED_LENGTH, max = DESIRED_LENGTH, message = ERROR_LENGTH)
    @JsonProperty(value = "cardId")
    private String cardId;

}
