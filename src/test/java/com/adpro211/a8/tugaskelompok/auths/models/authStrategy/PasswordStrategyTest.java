package com.adpro211.a8.tugaskelompok.auths.models.authStrategy;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class PasswordStrategyTest {

    PasswordStrategy passwordStrategy;

    @BeforeEach
    void setUp() {
        passwordStrategy = new PasswordStrategy();
        passwordStrategy.setPassword("12345");
    }

    @Test
    void getStrategyName() {
        assertEquals("password", passwordStrategy.getStrategyName());
    }

    @Test
    void authenticateSuccess() {
        Map<String, Object> authData = new HashMap<>();
        authData.put("password", "12345");
        assertTrue(passwordStrategy.authenticate(authData));
    }

    @Test
    void authenticateFailBadRequest() {
        Map<String, Object> authData = new HashMap<>();
        authData.put("passwfffford", "12345");
        assertFalse(passwordStrategy.authenticate(authData));
    }

    @Test
    void authenticateFailWrongPassword() {
        Map<String, Object> authData = new HashMap<>();
        authData.put("password", "abcd");
        assertFalse(passwordStrategy.authenticate(authData));
    }
}