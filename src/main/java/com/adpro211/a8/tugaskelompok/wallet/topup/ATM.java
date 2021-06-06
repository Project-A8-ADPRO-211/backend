package com.adpro211.a8.tugaskelompok.wallet.topup;

import com.adpro211.a8.tugaskelompok.wallet.models.Wallet;

import java.util.Map;
import com.adpro211.a8.tugaskelompok.wallet.repository.WalletRepository;

import org.springframework.beans.factory.annotation.Autowired;

public class ATM implements Topup {
    @Autowired
    WalletRepository walletRepository;

    @Override
    public String getType() {
        return "ATM";
    }

    @Override
    public Wallet topup(Wallet wallet, Map<String, Object> requestBody) {
        Number amountObj = (Number) requestBody.get("amount");
        double amount = amountObj.doubleValue();
        String noRekening = requestBody.get("noRekening").toString();

        double balance = wallet.getBalance();
        balance += amount;
        wallet.setBalance(balance);
        return wallet;
    }
}
