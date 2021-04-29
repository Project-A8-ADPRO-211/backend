package com.adpro211.a8.tugaskelompok.auths.service;

import com.adpro211.a8.tugaskelompok.auths.models.account.Account;

public interface JWTService {
    public String generateToken(Account account);
    public String verifyToken(String authorizationToken);
}
