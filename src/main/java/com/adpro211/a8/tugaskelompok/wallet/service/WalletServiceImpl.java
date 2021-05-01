package com.adpro211.a8.tugaskelompok.wallet.service;

import com.adpro211.a8.tugaskelompok.wallet.models.Wallet;
import com.adpro211.a8.tugaskelompok.wallet.repository.WalletRepository;
import org.springframework.beans.factory.annotation.Autowired;

public class WalletServiceImpl implements WalletService {
    @Autowired
    WalletRepository walletRepository;

    @Override
    public Wallet createWallet() {
        Wallet wallet = new Wallet();
        walletRepository.save(wallet);
        return wallet;
    }

    @Override
    public Wallet getWalletById(int id) {
        return walletRepository.findWalletById(id);
    }
}
