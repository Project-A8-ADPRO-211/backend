package com.adpro211.a8.tugaskelompok.wallet.service;

import com.adpro211.a8.tugaskelompok.auths.models.account.Account;
import com.adpro211.a8.tugaskelompok.auths.models.account.Buyer;
import com.adpro211.a8.tugaskelompok.auths.repository.AccountRepository;
import com.adpro211.a8.tugaskelompok.wallet.models.Wallet;
import com.adpro211.a8.tugaskelompok.wallet.repository.TransactionRepository;
import com.adpro211.a8.tugaskelompok.wallet.repository.WalletRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class WalletServiceTest {

    @InjectMocks
    private WalletServiceImpl walletService;

    @Mock
    private WalletRepository walletRepository;

    @Mock
    private AccountRepository accountRepository;

    @Mock
    private TransactionRepository transactionRepository;

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
        wallet.setTransactions(new ArrayList<>());
    }

    @Test
    public void testWalletServiceCreateWallet() {
        when(accountRepository.save(any())).thenReturn(null);
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
        verify(transactionRepository, times(1)).save(any());
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
        verify(transactionRepository, times(1)).save(any());
    }

    @Test
    public void testWalletServiceWithdrawWalletBalanceNotEnough() {
        assertEquals(10, wallet.getBalance());

        Map<String, Object> json = new HashMap<>();
        json.put("amount", 40);
        json.put("noRekening", "0123456");

        walletService.withdrawWallet(wallet, json);

        assertEquals(10, wallet.getBalance());
        verify(transactionRepository, times(0)).save(any());
    }

    @Test
    public void testWalletServiceWithdrawWalletSuccessful() {
        assertEquals(10, wallet.getBalance());

        Map<String, Object> json = new HashMap<>();
        json.put("amount", 10);
        json.put("noRekening", "0123456");

        walletService.withdrawWallet(wallet, json);

        assertEquals(0, wallet.getBalance());
        verify(transactionRepository, times(1)).save(any());
    }

    @Test
    public void testWalletServiceGetTransactionByWallet() {
        assertEquals(new ArrayList<>(), walletService.getTransactionByWallet(wallet));
    }

    @Test
    public void testWalletServiceCreateTransaction() {
        Map<String, Object> json = new HashMap<>();
        json.put("amount", 10);

        walletService.createTransaction(wallet, "mockType", json);

        verify(transactionRepository, times(1)).save(any());
    }

    @Test
    public void testWalletServiceGetWalletById() {
        when(walletRepository.findWalletById(1)).thenReturn(wallet);

        assertEquals(wallet, walletService.getWalletById(wallet.getId()));
    }
}