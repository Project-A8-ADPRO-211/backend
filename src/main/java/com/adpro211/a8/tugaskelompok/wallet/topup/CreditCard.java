package com.adpro211.a8.tugaskelompok.wallet.topup;

import com.adpro211.a8.tugaskelompok.wallet.models.Wallet;

import java.util.Map;

public class CreditCard implements Topup {

    @Override
    public String getType() {
        return "Credit Card";
    }

    @Override
    public Wallet topup(Wallet wallet, Map<String, Object> requestBody) {
        double amount = Double.parseDouble(requestBody.get("amount").toString());
        String noKartu = requestBody.get("noKartu").toString();
        String cvv = requestBody.get("cvv").toString();

        double balance = wallet.getBalance();
        balance += amount;
        wallet.setBalance(balance);
        return wallet;
    }
}
