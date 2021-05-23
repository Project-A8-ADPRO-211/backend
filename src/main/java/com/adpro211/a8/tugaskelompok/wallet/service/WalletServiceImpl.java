package com.adpro211.a8.tugaskelompok.wallet.service;

import com.adpro211.a8.tugaskelompok.auths.models.account.Account;
import com.adpro211.a8.tugaskelompok.auths.models.account.Administrator;
import com.adpro211.a8.tugaskelompok.auths.models.account.Buyer;
import com.adpro211.a8.tugaskelompok.auths.models.account.Seller;
import com.adpro211.a8.tugaskelompok.auths.service.AccountService;
import com.adpro211.a8.tugaskelompok.wallet.models.Wallet;
import com.adpro211.a8.tugaskelompok.wallet.repository.WalletRepository;
import com.adpro211.a8.tugaskelompok.wallet.topup.ATM;
import com.adpro211.a8.tugaskelompok.wallet.topup.CreditCard;
import com.adpro211.a8.tugaskelompok.wallet.topup.Topup;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class WalletServiceImpl implements WalletService {

    @Autowired
    private WalletRepository walletRepository;

    @Autowired
    private AccountService accountService;

    @Override
    public Wallet createWallet(Account account) {
        Wallet wallet = new Wallet();
        wallet.setAccount(account);
        walletRepository.save(wallet);
        return wallet;
    }

    @Override
    public Wallet topupWallet(Wallet wallet, String type, Map<String, Object> requestBody) {
        Topup topup;
        switch (type) {
            case "ATM":
                topup = new ATM();
                break;
            default:
                topup = new CreditCard();
                break;
        }
        Wallet walletBaru = topup.topup(wallet, requestBody);
        walletRepository.save(walletBaru);
        return walletBaru;
    }

    @Override
    public Wallet withdrawWallet(Wallet wallet, Map<String, Object> requestBody) {
        double balance = wallet.getBalance();
        Number amountObj = (Number) requestBody.get("amount");
        double amount = amountObj.doubleValue();

        double currentBal = balance - amount;
        if (currentBal >= 0) wallet.setBalance(currentBal);

        return wallet;
    }

    @Override
    public Wallet getWalletById(int id) {
        return walletRepository.findById(id).orElse(null);
    }
}
