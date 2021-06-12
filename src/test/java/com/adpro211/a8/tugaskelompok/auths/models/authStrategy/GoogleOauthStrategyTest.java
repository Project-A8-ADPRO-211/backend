package com.adpro211.a8.tugaskelompok.auths.models.authStrategy;

import com.adpro211.a8.tugaskelompok.auths.util.OAuthVerifier;
import com.adpro211.a8.tugaskelompok.auths.utls.FakeOAuthVerifier;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class GoogleOauthStrategyTest {

    GoogleOauthStrategy oauthStrategy;

    @Mock
    GoogleIdTokenVerifier oAuthVerifier;

    @Mock
    GoogleIdToken idTokenMock;

    @BeforeEach
    void setUp() {
        oauthStrategy = new GoogleOauthStrategy();
        oauthStrategy.setUid("1");
    }

    @Test
    void getStrategyName() {
        assertEquals("google", oauthStrategy.getStrategyName());
    }

    @Test
    void checkVerifier() {
        OAuthVerifier.construct("dfdf");
        GoogleOauthStrategy.setVerifier(null);
        assertEquals(OAuthVerifier.class.getName() ,GoogleOauthStrategy.getVerifier().getClass().getName());
    }

    @Test
    void checkVerifyWrongToken() throws GeneralSecurityException, IOException {
        assertNotNull(oAuthVerifier);
        when(oAuthVerifier.verify(anyString())).thenReturn(null);
        OAuthVerifier.forceSetVerifier(oAuthVerifier);

        assertThrows(ResponseStatusException.class, () -> {
            OAuthVerifier.getInstance().verifyToken("fnjnfdsj");
        });
    }

    @Test
    void checkVerifyCorrectToken() throws GeneralSecurityException, IOException {
        GoogleIdToken.Payload payloadMock = new GoogleIdToken.Payload();
        when(oAuthVerifier.verify(anyString())).thenReturn(idTokenMock);
        when(idTokenMock.getPayload()).thenReturn(payloadMock);
        payloadMock.setEmail("abc@def.com");
        payloadMock.setSubject("abc");
        payloadMock.set("name", "abc");
        OAuthVerifier.forceSetVerifier(oAuthVerifier);

        OAuthVerifier.GoogleOAuthData data = OAuthVerifier.getInstance().verifyToken("kfjjsdnf");
        assertEquals(data.getEmail(), "abc@def.com");
        assertEquals(data.getUid(), "abc");
        assertEquals(data.getName(), "abc");
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