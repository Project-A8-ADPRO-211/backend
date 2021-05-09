package com.adpro211.a8.tugaskelompok.wallet.topup;

import com.adpro211.a8.tugaskelompok.wallet.models.Wallet;

import java.util.Map;

public interface Topup extends Strategy{
    void topup(Wallet wallet, Map<String, Object> requestBody);
}
