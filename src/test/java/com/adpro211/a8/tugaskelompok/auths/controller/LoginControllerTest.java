package com.adpro211.a8.tugaskelompok.auths.controller;

import com.adpro211.a8.tugaskelompok.auths.models.account.Account;
import com.adpro211.a8.tugaskelompok.auths.models.account.Buyer;
import com.adpro211.a8.tugaskelompok.auths.models.authStrategy.PasswordStrategy;
import com.adpro211.a8.tugaskelompok.auths.repository.AccountRepository;
import com.adpro211.a8.tugaskelompok.auths.repository.AdminRepository;
import com.adpro211.a8.tugaskelompok.auths.repository.BuyerRepository;
import com.adpro211.a8.tugaskelompok.auths.repository.SellerRepository;
import com.adpro211.a8.tugaskelompok.auths.service.AccountService;
import com.adpro211.a8.tugaskelompok.auths.service.AuthService;
import com.adpro211.a8.tugaskelompok.auths.service.JWTService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


import static org.junit.jupiter.api.Assertions.*;

@WebMvcTest(controllers = LoginController.class)
class LoginControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private AccountService accountService;

    @MockBean
    private AuthService authService;

    @MockBean
    private JWTService jwtService;

    @MockBean
    private AccountRepository accountRepository;

    @MockBean
    private AdminRepository adminRepository;

    @MockBean
    private BuyerRepository buyerRepository;

    @MockBean
    private SellerRepository sellerRepository;


    private Account account;
    private PasswordStrategy passwordStrategy;

    private String mapToJson(Object obj) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.writeValueAsString(obj);
    }

    static class LoginData {
        public String email;
        public String password;

        public LoginData(String email, String password) {
            this.email = email;
            this.password = password;
        }
    }

    static class WrongLoginData {
        public String username;
        public String password;

        public WrongLoginData(String email, String password) {
            this.username = email;
            this.password = password;
        }
    }

    @BeforeEach
    void setUp() {
        account = new Buyer();
        account.setName("Test1");
        account.setId(1);
        account.setEmail("abc@def.com");

        passwordStrategy = new PasswordStrategy();
        passwordStrategy.setAssignedUser(account);
        passwordStrategy.setPassword("1234");
    }

    @Test
    void testControllerLoginSuccess() throws Exception {
        when(accountService.getAccountByEmail("abc@def.com")).thenReturn(this.account);
        when(authService.login(eq(this.account), eq("password"), any())).thenReturn(true);

        mvc.perform(post("/login?strategy=password").contentType(MediaType.APPLICATION_JSON_VALUE).content(
                mapToJson(new LoginData("abc@def.com", "1234")))).andExpect(status().is2xxSuccessful());
    }

    @Test
    void testControllerLoginFailWrongPass() throws Exception {
        when(accountService.getAccountByEmail("abc@def.com")).thenReturn(this.account);
        when(authService.login(eq(this.account), eq("password"), any())).thenReturn(false);

        mvc.perform(post("/login?strategy=password").contentType(MediaType.APPLICATION_JSON_VALUE).content(
                mapToJson(new LoginData("abc@def.com", "1")))).andExpect(status().isNotFound());
    }

    // Both ^ v should have the same error

    @Test
    void testControllerLoginFailNotFound() throws Exception {
        when(accountService.getAccountByEmail("abc@def.com")).thenReturn(null);
        when(authService.login(eq(this.account), eq("password"), any())).thenReturn(false);

        mvc.perform(post("/login?strategy=password").contentType(MediaType.APPLICATION_JSON_VALUE).content(
                mapToJson(new LoginData("abc@def.com", "1")))).andExpect(status().isNotFound());
    }

    @Test
    void testControllerLoginBadRequest() throws Exception {
        when(accountService.getAccountByEmail("abc@def.com")).thenReturn(this.account);
        when(authService.login(eq(this.account), eq("password"), any())).thenReturn(false);

        mvc.perform(post("/login?strategy=password").contentType(MediaType.APPLICATION_JSON_VALUE).content(
                mapToJson(new WrongLoginData("abc@def.com", "1"))))
                .andExpect(status().isBadRequest());
    }
}