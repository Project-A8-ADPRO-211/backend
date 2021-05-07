package com.adpro211.a8.tugaskelompok.auths.controller;

import com.adpro211.a8.tugaskelompok.auths.models.account.Account;
import com.adpro211.a8.tugaskelompok.auths.models.account.Buyer;
import com.adpro211.a8.tugaskelompok.auths.models.account.Seller;
import com.adpro211.a8.tugaskelompok.auths.repository.AccountRepository;
import com.adpro211.a8.tugaskelompok.auths.repository.AdminRepository;
import com.adpro211.a8.tugaskelompok.auths.repository.BuyerRepository;
import com.adpro211.a8.tugaskelompok.auths.repository.SellerRepository;
import com.adpro211.a8.tugaskelompok.auths.resolver.AdminAccountFromTokenResolver;
import com.adpro211.a8.tugaskelompok.auths.resolver.BaseAccountFromTokenResolver;
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

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = ManageAccountController.class)
class ManageAccountControllerTest {

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

    @Mock
    private Account account2;

    private String mapToJson(Object obj) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.writeValueAsString(obj);
    }

    @BeforeEach
    void setUp() {

        account1 = new Buyer();
        account1.setName("Test1");
        account1.setId(1);
        account1.setEmail("abc@def.com");
        account1.setAccountType("buyer");

//        mvc = MockMvcBuilders.standaloneSetup(manageAccountController).setCustomArgumentResolvers();
    }

    @Test
    void testControllerGetUserSuccess() throws Exception {
        when(accountService.getAccountById(1)).thenReturn(this.account1);
        when(jwtService.verifyToken(anyString())).thenReturn("1");

        mvc.perform(get("/account").contentType(MediaType.APPLICATION_JSON_VALUE)
                .header("Authorization", "aaaaaaaaa")).andExpect(status().is2xxSuccessful());
    }

    @Test
    void testControllerGetUserNoToken() throws Exception {
        when(accountService.getAccountById(1)).thenReturn(this.account1);
        when(jwtService.verifyToken(anyString())).thenReturn("1");

        mvc.perform(get("/account").contentType(MediaType.APPLICATION_JSON_VALUE))
        .andExpect(status().isBadRequest());
    }

    @Test
    void testControllerGetUserBadToken() throws Exception {
        when(accountService.getAccountById(1)).thenReturn(this.account1);
        when(jwtService.verifyToken(anyString())).thenReturn(null);

        mvc.perform(get("/account").contentType(MediaType.APPLICATION_JSON_VALUE).header("Authorization", "aaaaaaaaa"))
                .andExpect(status().isForbidden());
    }

    @Test
    void testControllerGetUserProbablyImpossibleError() throws Exception {
        when(accountService.getAccountById(1)).thenReturn(this.account1);
        when(jwtService.verifyToken(anyString())).thenReturn("abhbsh");

        mvc.perform(get("/account").contentType(MediaType.APPLICATION_JSON_VALUE).header("Authorization", "aaaaaaaaa"))
                .andExpect(status().isInternalServerError());
    }


    @Test
    void testControllerGetUserAlreadyDeleted() throws Exception {
        when(accountService.getAccountById(1)).thenReturn(null);
        when(jwtService.verifyToken(anyString())).thenReturn("1");

        mvc.perform(get("/account").contentType(MediaType.APPLICATION_JSON_VALUE).header("Authorization", "aaaaaaaaa"))
                .andExpect(status().isForbidden());
    }

//    @Test
//    void testControllerUpdateUserSuccess() throws Exception {
//
//        Buyer data = new Buyer();
//        data.setAlamat("asdddju");
//        data.setId(2);
//        data.setName("assss");
//        data.setEmail("sddfff@fmfmk.vv");
//
//        when(accountService.getAccountById(1)).thenReturn(this.account1);
//        when(jwtService.verifyToken(anyString())).thenReturn("1");
//        when(accountService.updateAccount(1, this.account1)).thenReturn(account1);
//
//        mvc.perform(put("/account").contentType(MediaType.APPLICATION_JSON_VALUE).header("Authorization", "aaaaaaaaa")
//        .content(mapToJson(data))).andExpect(status().isOk());
//    }

    @Test
    void testControllerUpdateBuyerSuccess() throws Exception {
        Buyer data = new Buyer();
        data.setAlamat("asdddju");
        data.setId(2);
        data.setName("assss");
        data.setEmail("sddfff@fmfmk.vv");

        when(accountService.getAccountById(2)).thenReturn(this.account2);
        when(jwtService.verifyToken(anyString())).thenReturn("2");
        when(accountService.updateAccount(2, this.account2)).thenReturn(account2);
        when(buyerRepository.findBuyerById(anyInt())).thenReturn(data);
        when(account2.getAccountType()).thenReturn("buyer");

        mvc.perform(put("/account/updateBuyer").contentType(MediaType.APPLICATION_JSON_VALUE).header("Authorization", "aaaaaaaaa")
                .content(mapToJson(data))).andExpect(status().isOk());
    }

    @Test
    void testControllerUpdateBuyerNotABuyer() throws Exception {

        Buyer data = new Buyer();
        data.setAlamat("asdddju");
        data.setId(2);
        data.setName("assss");
        data.setEmail("sddfff@fmfmk.vv");

        when(accountService.getAccountById(2)).thenReturn(this.account2);
        when(jwtService.verifyToken(anyString())).thenReturn("2");
        when(accountService.updateAccount(2, this.account2)).thenReturn(account2);
        when(buyerRepository.findBuyerById(anyInt())).thenReturn(data);
        when(account2.getAccountType()).thenReturn("seller");


        mvc.perform(put("/account/updateBuyer").contentType(MediaType.APPLICATION_JSON_VALUE).header("Authorization", "aaaaaaaaa")
                .content(mapToJson(data))).andExpect(status().isForbidden());
    }

    @Test
    void testControllerUpdatePasswordSuccess() throws Exception {

        ManageAccountController.PasswordReset data = new ManageAccountController.PasswordReset();
        data.setNewPassword("snjnjdnjd");

        when(accountService.getAccountById(anyInt())).thenReturn(this.account1);
        when(jwtService.verifyToken(anyString())).thenReturn("2");
        when(accountService.updateAccountPass(any(), anyString())).thenReturn(account1);
        when(account2.getAccountType()).thenReturn("seller");


        mvc.perform(post("/account/updatePass").contentType(MediaType.APPLICATION_JSON_VALUE).header("Authorization", "aaaaaaaaa")
                .content(mapToJson(data))).andExpect(status().isOk());
    }
}