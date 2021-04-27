package com.adpro211.a8.tugaskelompok.auths.models.authStrategy;

import java.util.Map;

public class PasswordStrategy extends AuthStrategy {

    @Override
    public String getName() {
        return "password";
    }

    private String password;

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    boolean authenticate(Map<String, Object> requestBody) {
        return requestBody.containsKey("password") && requestBody.get("password").toString().equals(this.password);
    }
}
