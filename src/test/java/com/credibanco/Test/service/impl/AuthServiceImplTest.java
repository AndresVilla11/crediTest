package com.credibanco.Test.service.impl;

import com.credibanco.Test.exceptions.BadRequestException;
import com.credibanco.Test.exceptions.DataBaseException;
import com.credibanco.Test.exceptions.NotFoundException;
import com.credibanco.Test.jwt.JwtService;
import com.credibanco.Test.model.auth.AuthResponse;
import com.credibanco.Test.model.dao.UserDao;
import com.credibanco.Test.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.authentication.AuthenticationManager;

import java.util.List;
import java.util.Optional;

import static com.credibanco.Test.ObjectsMock.UserRefreshDtoComplete;
import static com.credibanco.Test.ObjectsMock.token;
import static com.credibanco.Test.ObjectsMock.userDaoComplete;
import static com.credibanco.Test.ObjectsMock.userRegisterDtoComplete;
import static com.credibanco.Test.ObjectsMock.userRegisterSameIdDtoComplete;
import static com.credibanco.Test.util.Constant.ERROR_REFRESH;
import static com.credibanco.Test.util.Constant.ERROR_SAVE;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

class AuthServiceImplTest {
    @Mock
    UserRepository userRepository;
    @Mock
    JwtService jwtService;
    @Mock
    AuthenticationManager authenticationManager;
    @InjectMocks
    AuthServiceImpl authService;

    @BeforeEach
    public void createMocks() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void refresh() {

        when(userRepository.findByUsername(anyString())).thenReturn(userDaoComplete());
        when(jwtService.getToken(any(UserDao.class))).thenReturn(token());

        AuthResponse refresh = authService.refresh(UserRefreshDtoComplete());

        assertEquals(token(), refresh.getToken());
    }

    @Test
    void refreshUserException() {

        when(userRepository.findByUsername(anyString())).thenReturn(Optional.empty());

        Exception exception = assertThrows(NotFoundException.class, () -> {
            authService.refresh(UserRefreshDtoComplete());
        });

        assertEquals("Not Found: Doesn't exist user", exception.getMessage());
    }

    @Test
    void refreshException() {

        when(userRepository.findByUsername(anyString())).thenReturn(userDaoComplete());
        when(jwtService.getToken(any(UserDao.class))).thenThrow(RuntimeException.class);

        Exception exception = assertThrows(RuntimeException.class, () -> {
            authService.refresh(UserRefreshDtoComplete());
        });

        assertEquals(ERROR_REFRESH, exception.getMessage());
    }

    @Test
    void register() {

        when(userRepository.findAll()).thenReturn(List.of(userDaoComplete().get()));
        when(userRepository.save(any(UserDao.class))).thenReturn(userDaoComplete().get());
        when(jwtService.getToken(any(UserDao.class))).thenReturn(token());

        AuthResponse register = authService.register(userRegisterDtoComplete());

        assertEquals(token(), register.getToken());

    }

    @Test
    void registerBadRequest() {

        when(userRepository.findAll()).thenReturn(List.of(userDaoComplete().get()));
        when(userRepository.save(any(UserDao.class))).thenReturn(userDaoComplete().get());

        Exception exception = assertThrows(BadRequestException.class, () -> {
            authService.register(userRegisterSameIdDtoComplete());
        });

        assertEquals("Bad Request User exist!", exception.getMessage());

    }

    @Test
    void registerRequestException() {

        when(userRepository.findAll()).thenReturn(List.of(userDaoComplete().get()));
        when(userRepository.save(any(UserDao.class))).thenReturn(userDaoComplete().get());
        when(userRepository.save(any(UserDao.class))).thenThrow(DataBaseException.class);

        Exception exception = assertThrows(DataBaseException.class, () -> {
            authService.register(userRegisterDtoComplete());
        });

        assertEquals(ERROR_SAVE, exception.getMessage());

    }
}