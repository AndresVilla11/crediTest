package com.credibanco.Test.controller;

import com.credibanco.Test.model.auth.AuthResponse;
import com.credibanco.Test.model.auth.UserRefresh;
import com.credibanco.Test.model.auth.UserRegister;
import com.credibanco.Test.service.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/auth")
public class AuthController {

    private final AuthService authService;

    @PostMapping(value = "/refresh")
    @ResponseStatus(HttpStatus.OK)
    private AuthResponse refresh(@Valid @RequestBody UserRefresh userRefresh) {
        return authService.refresh(userRefresh);
    }

    @PostMapping(value = "/register")
    @ResponseStatus(HttpStatus.CREATED)
    private AuthResponse register(@Valid @RequestBody UserRegister userRegister) {
        return authService.register(userRegister);
    }

}
