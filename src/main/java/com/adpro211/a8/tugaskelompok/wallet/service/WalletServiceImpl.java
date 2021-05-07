package com.adpro211.a8.tugaskelompok.wallet.service;

import com.adpro211.a8.tugaskelompok.auths.models.account.Account;
import com.adpro211.a8.tugaskelompok.auths.service.AccountService;
import com.adpro211.a8.tugaskelompok.wallet.models.Wallet;
import com.adpro211.a8.tugaskelompok.wallet.repository.WalletRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class WalletServiceImpl implements WalletService {

    @Autowired
    private WalletRepository walletRepository;

    @Autowired
    private AccountService accountService;

    @Override
    public Wallet createWallet(int id) {
        Wallet wallet = new Wallet();
        Account account = accountService.getAccountById(id);
        wallet.setAccount(account);
        walletRepository.save(wallet);
        return wallet;
    }

    @Override
    public Wallet getWalletById(int id) {
        return walletRepository.findById(id).orElse(null);
    }
}
