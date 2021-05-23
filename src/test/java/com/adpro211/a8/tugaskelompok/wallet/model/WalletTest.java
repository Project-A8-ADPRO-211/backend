package com.adpro211.a8.tugaskelompok.wallet.model;

import com.adpro211.a8.tugaskelompok.auths.models.account.Account;
import com.adpro211.a8.tugaskelompok.auths.models.account.Buyer;
import com.adpro211.a8.tugaskelompok.wallet.models.Wallet;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class WalletTest {
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
    public void testWalletGetId() {
        assertEquals(1, wallet.getId());
    }

    @Test
    public void testWalletGetAccount() {
        assertEquals(account, wallet.getAccount());
    }

    @Test
    public void testWalletGetBalance() {
        assertEquals(10, wallet.getBalance());
    }
}
