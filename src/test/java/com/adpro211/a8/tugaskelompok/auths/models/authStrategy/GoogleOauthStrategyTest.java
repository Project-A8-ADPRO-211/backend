package com.adpro211.a8.tugaskelompok.auths.models.authStrategy;

import com.adpro211.a8.tugaskelompok.auths.util.OAuthVerifier;
import com.adpro211.a8.tugaskelompok.auths.utls.FakeOAuthVerifier;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class GoogleOauthStrategyTest {

    GoogleOauthStrategy oauthStrategy;

    @BeforeEach
    void setUp() {
        oauthStrategy = new GoogleOauthStrategy();
        oauthStrategy.setUid("1");
    }

    @Test
    void authenticateSuccess() {
        OAuthVerifier.GoogleOAuthData data = new OAuthVerifier.GoogleOAuthData("a", "a@bdd.com", "1");
        GoogleOauthStrategy.setVerifier(new FakeOAuthVerifier(data));

        Map<String, Object> authData = new HashMap<>();
        authData.put("token", "12345");
        assertTrue(oauthStrategy.authenticate(authData));
    }
}