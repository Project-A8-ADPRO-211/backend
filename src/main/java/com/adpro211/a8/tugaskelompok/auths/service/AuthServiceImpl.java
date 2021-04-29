package com.adpro211.a8.tugaskelompok.auths.service;

import com.adpro211.a8.tugaskelompok.auths.models.account.Account;
import com.adpro211.a8.tugaskelompok.auths.models.authStrategy.AuthStrategy;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class AuthServiceImpl implements AuthService{

    @Override
    public boolean login(Account account, String strategyName, Map<String, Object> requestBody) {
        for (AuthStrategy strategy : account.getAuthStrategies()) {
            if (!strategy.getStrategyName().equals(strategyName)) continue;
            return strategy.authenticate(requestBody);
        }
        return false;
    }

    @Override
    public boolean bindMethod(Account account, String strategy, Map<String, Object> requestBody) {
        return false;
    }
}
