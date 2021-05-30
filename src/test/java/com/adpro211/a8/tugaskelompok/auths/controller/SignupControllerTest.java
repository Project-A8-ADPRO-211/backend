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
import com.adpro211.a8.tugaskelompok.email.service.MailgunSenderImpl;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = SignupController.class)
class SignupControllerTest {

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

    private SignupController.UserSignUp signup = new SignupController.UserSignUp();
    private SignupController.UserSignUpGoogle signUpGoogle = new SignupController.UserSignUpGoogle();
    private Account account;
    private PasswordStrategy passwordStrategy;

    private String mapToJson(Object obj) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.writeValueAsString(obj);
    }



    @BeforeEach
    void setUp() {
        MailgunSenderImpl.sendRealEmail(false);
        MailgunSenderImpl.sendRealEmailRequest(true);
        account = new Buyer();
        account.setName("Test1");
        account.setId(1);
        account.setEmail("abc@def.com");
        
        signup.setEmail("ssss");
        signup.setName("ssss");
        signup.setPassword("dddd");
        signup.setType("ssss");

        signUpGoogle.setAccType("admin");
        signUpGoogle.setToken("abcd");
    }

    @Test
    void testControllerSignUpSuccess() throws Exception {
        when(accountService.createNewAccount(any(), any(), any(), any())).thenReturn(this.account);

        mvc.perform(post("/signup").contentType(MediaType.APPLICATION_JSON_VALUE).content(
                mapToJson(signup))).andExpect(status().is2xxSuccessful());
    }

    @Test
    void testControllerSignUpGoogleSuccess() throws Exception {
        when(accountService.createNewAccountGoogle(any(), any())).thenReturn(this.account);

        mvc.perform(post("/signup/oauth").contentType(MediaType.APPLICATION_JSON_VALUE).content(
                mapToJson(signup))).andExpect(status().is2xxSuccessful());
    }

}