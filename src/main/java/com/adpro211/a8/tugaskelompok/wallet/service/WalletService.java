package com.adpro211.a8.tugaskelompok.wallet.service;

import com.adpro211.a8.tugaskelompok.auths.models.account.Account;
import com.adpro211.a8.tugaskelompok.wallet.models.Transaction;
import com.adpro211.a8.tugaskelompok.wallet.models.Wallet;

import java.util.Map;

public interface WalletService {
    Wallet createWallet(Account account);
    Wallet topupWallet(Wallet wallet, String type, Map<String, Object> requestBody);
    Wallet withdrawWallet(Wallet wallet, Map<String, Object> requestBody);
    Wallet getWalletById(int id);
    Iterable<Transaction> getTransactionByWallet(Wallet wallet);
}
