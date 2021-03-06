package com.adpro211.a8.tugaskelompok.wallet.controller;

import com.adpro211.a8.tugaskelompok.auths.models.account.Account;
import com.adpro211.a8.tugaskelompok.auths.models.account.Buyer;
import com.adpro211.a8.tugaskelompok.auths.repository.AccountRepository;
import com.adpro211.a8.tugaskelompok.auths.repository.AdminRepository;
import com.adpro211.a8.tugaskelompok.auths.repository.BuyerRepository;
import com.adpro211.a8.tugaskelompok.auths.repository.SellerRepository;
import com.adpro211.a8.tugaskelompok.auths.service.AccountService;
import com.adpro211.a8.tugaskelompok.auths.service.AuthService;
import com.adpro211.a8.tugaskelompok.auths.service.JWTService;
import com.adpro211.a8.tugaskelompok.wallet.models.Wallet;
import com.adpro211.a8.tugaskelompok.wallet.repository.WalletRepository;
import com.adpro211.a8.tugaskelompok.wallet.service.WalletService;
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

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = WalletController.class)
public class WalletControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private AccountService accountService;

    @MockBean
    private AuthService authService;

    @MockBean
    private JWTService jwtService;

    @MockBean
    private WalletService walletService;

    @MockBean
    private AccountRepository accountRepository;

    @MockBean
    private AdminRepository adminRepository;

    @MockBean
    private BuyerRepository buyerRepository;

    @MockBean
    private SellerRepository sellerRepository;

    @MockBean
    private WalletRepository walletRepository;

    private Account account;
    private Wallet wallet;

    private String mapToJson(Object obj) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.writeValueAsString(obj);
    }

    static class TopupWithATMData {
        public double amount;
        public String noRekening;

        public TopupWithATMData(double amount, String noRekening) {
            this.amount = amount;
            this.noRekening = noRekening;
        }
    }

    static class TopUpWithCreditCardData {
        public double amount;
        public String noKartu;
        public String cvv;

        public TopUpWithCreditCardData(double amount, String noKartu, String cvv) {
            this.amount = amount;
            this.noKartu = noKartu;
            this.cvv = cvv;
        }
    }

    static class WithdrawData {
        public double amount;
        public String noRekening;

        public WithdrawData(double amount, String noRekening) {
            this.amount = amount;
            this.noRekening = noRekening;
        }
    }

    @BeforeEach
    public void setup() {
        account = new Buyer();
        account.setName("mockName");
        account.setId(1);
        account.setEmail("aaa@aaa.com");
        account.setAccountType("seller");

        wallet = new Wallet();
        wallet.setId(1);
        wallet.setBalance(10);

        wallet.setAccount(account);
        account.setWallet(wallet);
    }

    @Test
    public void testWalletControllerTopUpWalletWithATMSuccess() throws Exception {
        when(accountService.getAccountById(1)).thenReturn(account);
        when(jwtService.verifyToken(anyString())).thenReturn("1");

        mvc.perform(post("/wallet/topup")
                .param("strategy", "ATM")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .header("Authorization", "aaaaa")
                .content(mapToJson(new TopupWithATMData(
                        10, "0123456"
                )))).andExpect(status().isOk());
    }

    @Test
    public void testWalletControllerTopUpWalletWithCreditCardSuccess() throws Exception {
        when(accountService.getAccountById(1)).thenReturn(account);
        when(jwtService.verifyToken(anyString())).thenReturn("1");

        mvc.perform(post("/wallet/topup")
                .param("strategy", "CreditCard")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .header("Authorization", "aaaaa")
                .content(mapToJson(new TopUpWithCreditCardData(
                        10,"0123456","456"
                )))).andExpect(status().isOk());
    }

    @Test
    public void testWalletControllerWithdrawWalletSuccess() throws Exception {
        when(accountService.getAccountById(1)).thenReturn(account);
        when(jwtService.verifyToken(anyString())).thenReturn("1");

        mvc.perform(post("/wallet/withdraw")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .header("Authorization", "aaaaa")
                .content(mapToJson(new WithdrawData(
                        10, "0123456"
                )))).andExpect(status().isOk());
    }

    @Test
    public void testWalletControllerGetWallet() throws Exception {
        when(accountService.getAccountById(1)).thenReturn(account);
        when(jwtService.verifyToken(anyString())).thenReturn("1");

        mvc.perform(get("/wallet/")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .header("Authorization", "aaaaa"))
                .andExpect(status().is2xxSuccessful());
    }

    @Test
    public void testWalletControllerGetTransaction() throws Exception {
        when(accountService.getAccountById(1)).thenReturn(account);
        when(jwtService.verifyToken(anyString())).thenReturn("1");

        mvc.perform(get("/wallet/transaction")
                .contentType(MediaType.APPLICATION_JSON)
                .header("Authorization", "aaaaa"))
                .andExpect(status().is2xxSuccessful());
    }
}
