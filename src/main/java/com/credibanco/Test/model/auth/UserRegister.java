package com.credibanco.Test.model.auth;

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
public class UserRegister {
    @NotBlank(message = "Full name" + ERROR_DTO)
    @JsonProperty(value = "full_name")
    private String fullName;

    @NotBlank(message = "Identification number" + ERROR_DTO)
    @JsonProperty(value = "identification_number")
    private String identificationNumber;

    @NotBlank(message = "User name" + ERROR_DTO)
    @JsonProperty(value = "user_name")
    private String userName;
}

