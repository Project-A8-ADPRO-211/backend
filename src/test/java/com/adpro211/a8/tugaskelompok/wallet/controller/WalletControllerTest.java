package com.adpro211.a8.tugaskelompok.wallet.controller;

import com.adpro211.a8.tugaskelompok.auths.controller.SignupController;
import com.adpro211.a8.tugaskelompok.auths.models.account.Account;
import com.adpro211.a8.tugaskelompok.auths.models.account.Buyer;
import com.adpro211.a8.tugaskelompok.wallet.models.Wallet;
import com.adpro211.a8.tugaskelompok.wallet.service.WalletService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;


import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class WalletControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private WalletService walletService;

    private Account account;
    private Wallet wallet;
    private SignupController.UserSignUp signup = new SignupController.UserSignUp();

    @BeforeEach
    void setup() {
        account = new Buyer();
        account.setName("Test1");
        account.setId(1);
        account.setEmail("abc@def.com");

        wallet = new Wallet();
        wallet.setAccount(account);
    }
}
