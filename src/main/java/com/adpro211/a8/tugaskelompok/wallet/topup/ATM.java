package com.adpro211.a8.tugaskelompok.wallet.topup;

import com.adpro211.a8.tugaskelompok.wallet.models.Wallet;

import java.util.Map;

public class ATM implements Topup {

    @Override
    public String getType() {
        return "ATM";
    }

    @Override
    public void topup(Wallet wallet, Map<String, Object> requestBody) {
        Number amountObj = (Number) requestBody.get("amount");
        double amount = amountObj.doubleValue();
        String noRekening = requestBody.get("noRekening").toString();

        double balance = wallet.getBalance();
        balance += amount;
        wallet.setBalance(balance);
    }
}
