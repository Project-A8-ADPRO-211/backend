package com.adpro211.a8.tugaskelompok.auths.models.authStrategy;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import java.util.Map;

@Entity
@Data
@NoArgsConstructor
public class SsoUiStrategy extends AuthStrategy {
    @Override
    public String getStrategyName() {
        return "sso-ui";
    }

    @Override
    public boolean authenticate(Map<String, Object> requestBody) {
        return false;
    }
}
