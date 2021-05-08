package com.adpro211.a8.tugaskelompok.wallet.service;

import com.adpro211.a8.tugaskelompok.wallet.models.Wallet;

import java.util.Map;

public interface WalletService {
    Wallet createWallet(int id);
    Wallet topupWallet(Wallet wallet, String type, Map<String, Object> requestBody);
    Wallet getWalletById(int id);
}
