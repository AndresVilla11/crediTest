package com.credibanco.Test.model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import static com.credibanco.Test.util.Constant.ERROR_DTO;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProductDto {

    @NotBlank(message = "Product id" + ERROR_DTO)
    @Size(min = 6, max = 6)
    @JsonProperty(value = "productId")
    private String productId;
}
