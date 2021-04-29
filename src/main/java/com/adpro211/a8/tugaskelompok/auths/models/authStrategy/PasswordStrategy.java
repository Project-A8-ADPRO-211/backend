package com.adpro211.a8.tugaskelompok.auths.models.authStrategy;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import java.util.Map;

@Entity
@Data
@NoArgsConstructor
public class PasswordStrategy extends AuthStrategy {

    @Override
    public String getStrategyName() {
        return "password";
    }

    private String password;

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public boolean authenticate(Map<String, Object> requestBody) {
        return requestBody.containsKey("password") && requestBody.get("password").toString().equals(this.password);
    }
}
