package com.credibanco.Test.service.impl;

import com.credibanco.Test.exceptions.BadRequestException;
import com.credibanco.Test.exceptions.DataBaseException;
import com.credibanco.Test.exceptions.NotFoundException;
import com.credibanco.Test.jwt.JwtService;
import com.credibanco.Test.model.auth.AuthResponse;
import com.credibanco.Test.model.auth.Role;
import com.credibanco.Test.model.auth.UserRefresh;
import com.credibanco.Test.model.auth.UserRegister;
import com.credibanco.Test.model.dao.UserDao;
import com.credibanco.Test.repository.UserRepository;
import com.credibanco.Test.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;

import static com.credibanco.Test.util.Constant.ERROR_REFRESH;
import static com.credibanco.Test.util.Constant.ERROR_SAVE;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    @Override
    public AuthResponse refresh(UserRefresh userRefresh) {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(userRefresh.getUserName(), userRefresh.getIdentificationNumber()));
            UserDao userDetails = userRepository.findByUsername(userRefresh.getUserName())
                    .orElseThrow(() -> new NotFoundException("Doesn't exist user"));
            final String token = jwtService.getToken(userDetails);
            return AuthResponse.builder()
                    .token(token)
                    .build();
        } catch (NotFoundException exception) {
            throw exception;
        } catch (Exception exception) {
            throw new RuntimeException(ERROR_REFRESH);
        }
    }

    @Override
    public AuthResponse register(UserRegister userRegister) {

        String hashIdentificationNumber = BCrypt.hashpw(userRegister.getIdentificationNumber(), BCrypt.gensalt());

        boolean userNotExist = userRepository.findAll().stream()
                .anyMatch(userFind -> BCrypt.checkpw(userRegister.getIdentificationNumber(), userFind.getIdentificationNumber()));

        if (userNotExist) {
            throw new BadRequestException("User exist!");
        }

        UserDao userDao = UserDao.builder()
                .fullName(userRegister.getFullName())
                .identificationNumber(hashIdentificationNumber)
                .username(userRegister.getUserName())
                .role(Role.USER)
                .build();
        try {
            UserDao savedUser = userRepository.save(userDao);
            return AuthResponse.builder()
                    .token(jwtService.getToken(savedUser))
                    .build();
        } catch (Exception exception) {
            throw new DataBaseException(ERROR_SAVE);
        }
    }
}
