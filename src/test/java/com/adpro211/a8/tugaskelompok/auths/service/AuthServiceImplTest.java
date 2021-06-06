package com.adpro211.a8.tugaskelompok.auths.service;

import com.adpro211.a8.tugaskelompok.auths.models.account.Account;
import com.adpro211.a8.tugaskelompok.auths.models.authStrategy.AuthStrategy;
import com.adpro211.a8.tugaskelompok.auths.models.authStrategy.PasswordStrategy;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AuthServiceImplTest {

    @InjectMocks
    AuthServiceImpl authService;

    @Mock
    Account accountMock;

    @Mock
    AuthStrategy passwordStrategy;


    @Test
    void loginSuccess() {
        when(passwordStrategy.authenticate(any())).thenReturn(true);
        when(passwordStrategy.getStrategyName()).thenReturn("password");
        List<AuthStrategy> strategyList = new ArrayList<>();
        strategyList.add(passwordStrategy);
        when(accountMock.getAuthStrategies()).thenReturn(new HashSet<>(strategyList));

        assertTrue(authService.login(accountMock, "password", null));

    }

    @Test
    void loginNoStrategyName() {
        when(passwordStrategy.getStrategyName()).thenReturn("password");
        List<AuthStrategy> strategyList = new ArrayList<>();
        strategyList.add(passwordStrategy);
        when(accountMock.getAuthStrategies()).thenReturn(new HashSet<>(strategyList));

        assertFalse(authService.login(accountMock, "google", null));

    }
}