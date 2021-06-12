package com.adpro211.a8.tugaskelompok.auths.controller;

import com.adpro211.a8.tugaskelompok.auths.models.account.Account;
import com.adpro211.a8.tugaskelompok.auths.models.account.Administrator;
import com.adpro211.a8.tugaskelompok.auths.models.account.Buyer;
import com.adpro211.a8.tugaskelompok.auths.models.account.Seller;
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
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = TestAuthController.class)
class TestAuthControllerTest {

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

    private Account account1;

    private Buyer buyer;
    private Administrator administrator;
    private Seller seller;


    @BeforeEach
    void setUp() {

        buyer = new Buyer();
        buyer.setName("Test1");
        buyer.setId(1);
        buyer.setEmail("abc@def.com");
        buyer.setAccountType("buyer");

        account1 =  buyer;

        seller = new Seller();
        seller.setName("Test1");
        seller.setId(2);
        seller.setEmail("abcd@def.com");
        seller.setAccountType("seller");

        administrator = new Administrator();
        administrator.setName("Test1");
        administrator.setId(2);
        administrator.setEmail("abcd@def.com");
        administrator.setAccountType("admin");

//        mvc = MockMvcBuilders.standaloneSetup(manageAccountController).setCustomArgumentResolvers();
    }

    @Test
    void testControllerGetSeller() throws Exception {
        when(accountService.getAccountById(1)).thenReturn(this.seller);
        when(jwtService.verifyToken(anyString())).thenReturn("1");

        mvc.perform(get("/test/seller").contentType(MediaType.APPLICATION_JSON_VALUE)
                .header("Authorization", "aaaaaaaaa")).andExpect(status().is2xxSuccessful());
    }

    @Test
    void testControllerGetBase() throws Exception {
        when(accountService.getAccountById(1)).thenReturn(this.seller);
        when(jwtService.verifyToken(anyString())).thenReturn("1");

        mvc.perform(get("/test/base").contentType(MediaType.APPLICATION_JSON_VALUE)
                .header("Authorization", "aaaaaaaaa")).andExpect(status().is2xxSuccessful());
    }

    @Test
    void testControllerGetAdmin() throws Exception {
        when(accountService.getAccountById(1)).thenReturn(this.administrator);
        when(jwtService.verifyToken(anyString())).thenReturn("1");

        mvc.perform(get("/test/admin").contentType(MediaType.APPLICATION_JSON_VALUE)
                .header("Authorization", "aaaaaaaaa")).andExpect(status().is2xxSuccessful());
    }

    @Test
    void testControllerGetBuyer() throws Exception {
        when(accountService.getAccountById(1)).thenReturn(this.buyer);
        when(jwtService.verifyToken(anyString())).thenReturn("1");

        mvc.perform(get("/test/buyer").contentType(MediaType.APPLICATION_JSON_VALUE)
                .header("Authorization", "aaaaaaaaa")).andExpect(status().is2xxSuccessful());
    }

    @Test
    void testControllerGetAdminAsAccount() throws Exception {
        when(accountService.getAccountById(1)).thenReturn(this.administrator);
        when(jwtService.verifyToken(anyString())).thenReturn("1");

        mvc.perform(get("/test/adminasaccount").contentType(MediaType.APPLICATION_JSON_VALUE)
                .header("Authorization", "aaaaaaaaa")).andExpect(status().is2xxSuccessful());
    }

    @Test
    void testControllerGetWrongAccount() throws Exception {
        when(accountService.getAccountById(1)).thenReturn(this.administrator);
        when(jwtService.verifyToken(anyString())).thenReturn("1");

        mvc.perform(get("/test/wrong").contentType(MediaType.APPLICATION_JSON_VALUE)
                .header("Authorization", "aaaaaaaaa")).andExpect(status().is2xxSuccessful());
    }

}