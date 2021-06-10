package com.adpro211.a8.tugaskelompok.wallet.model;

import com.adpro211.a8.tugaskelompok.auths.models.account.Account;
import com.adpro211.a8.tugaskelompok.auths.models.account.Buyer;
import com.adpro211.a8.tugaskelompok.wallet.models.Transaction;
import com.adpro211.a8.tugaskelompok.wallet.models.Wallet;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class TransactionTest {

    private Transaction transaction;
    private Wallet wallet;
    LocalDateTime datetime;

    @BeforeEach
    public void setup() {
        this.wallet = new Wallet();

        this.transaction = new Transaction();
        this.transaction.setId(1);
        this.transaction.setAmount(10.0);
        this.transaction.setWallet(wallet);
        this.transaction.setType("Withdraw");
        this.datetime = LocalDateTime.now();
        this.transaction.setDateTime(datetime);
    }

    @Test
    public void testTransactionGetId() {
        assertEquals(1, transaction.getId());
    }

    @Test
    public void testTransactionGetAmount() {
        assertEquals(10.0, transaction.getAmount());
    }

    @Test
    public void testTransactionGetType() {
        assertEquals("Withdraw",transaction.getType());
    }

    @Test
    public void testTransactionGetWallet() {
        assertEquals(wallet, transaction.getWallet());
    }

    @Test
    public void testTransactionGetDateTime() {
        assertEquals(datetime, transaction.getDateTime());
    }
}