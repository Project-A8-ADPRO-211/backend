package com.adpro211.a8.tugaskelompok.wallet.service;

import com.adpro211.a8.tugaskelompok.auths.models.account.Account;
import com.adpro211.a8.tugaskelompok.auths.models.account.Buyer;
import com.adpro211.a8.tugaskelompok.wallet.models.Wallet;
import com.adpro211.a8.tugaskelompok.wallet.repository.WalletRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class WalletServiceTest {

    @InjectMocks
    private WalletServiceImpl walletService;

    @Mock
    private WalletRepository walletRepository;

    private Wallet wallet;
    private Account account;

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
    }

    @Test
    public void testWalletServiceCreateWallet() {
        Wallet wallet1 = walletService.createWallet(account);
        verify(walletRepository, times(1)).save(any());
    }

    @Test
    public void testWalletServiceTopUpWalletUsingATM() {
        assertEquals(10, wallet.getBalance());

        Map<String, Object> json = new HashMap<>();
        json.put("amount", 40);
        json.put("noRekening", "0123456");

        walletService.topupWallet(wallet, "ATM", json);

        assertEquals(50, wallet.getBalance());
    }

    @Test
    public void testWalletServiceTopUpWalletUsingCreditCard() {
        assertEquals(10, wallet.getBalance());

        Map<String, Object> json = new HashMap<>();
        json.put("amount", 40);
        json.put("noKartu", "0123456");
        json.put("cvv", "456");

        walletService.topupWallet(wallet, "CreditCard", json);

        assertEquals(50, wallet.getBalance());
    }
}
