package com.adpro211.a8.tugaskelompok.auths.service;

import com.adpro211.a8.tugaskelompok.auths.models.account.Account;

import java.util.Map;

public interface AuthService {
    public boolean login(Account account, String strategy, Map<String, Object> requestBody);
    public boolean bindMethod(Account account, String strategy, Map<String, Object> requestBody);
}
