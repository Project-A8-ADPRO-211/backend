package com.adpro211.a8.tugaskelompok.auths.models.authStrategy;

import at.favre.lib.crypto.bcrypt.BCrypt;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import java.util.Map;

@Entity
@NoArgsConstructor
public class PasswordStrategy extends AuthStrategy {

    @Override
    public String getStrategyName() {
        return "password";
    }

    private String password;

    public void setPassword(String password) {
        this.password = BCrypt.withDefaults().hashToString(12, password.toCharArray());
    }

    @Override
    public boolean authenticate(Map<String, Object> requestBody) {
        return requestBody.containsKey("password") &&
                BCrypt.verifyer().verify(requestBody.get("password").toString().toCharArray(), this.password).verified;
    }
}
