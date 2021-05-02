package com.adpro211.a8.tugaskelompok.wallet.service;

import com.adpro211.a8.tugaskelompok.wallet.models.Wallet;

public interface WalletService {
    Wallet createWallet(int id);
    Wallet topupWallet();
    Wallet getWalletById(int id);
}
