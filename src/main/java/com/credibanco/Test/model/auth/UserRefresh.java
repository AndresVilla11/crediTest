package com.credibanco.Test.model.auth;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserRefresh {

    @NotBlank
    @JsonProperty(value = "user_name")
    private String userName;

    @NotBlank
    @JsonProperty(value = "identification_number")
    private String identificationNumber;
}
