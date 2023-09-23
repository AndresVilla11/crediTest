package com.credibanco.Test.service;

import com.credibanco.Test.model.auth.AuthResponse;
import com.credibanco.Test.model.auth.UserRefresh;
import com.credibanco.Test.model.auth.UserRegister;

public interface AuthService {
    AuthResponse refresh(UserRefresh userRefresh);

    AuthResponse register(UserRegister userRegister);
}
