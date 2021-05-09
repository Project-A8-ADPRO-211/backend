package com.adpro211.a8.tugaskelompok.auths.util;

import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier;
import com.google.api.client.googleapis.testing.auth.oauth2.MockGoogleCredential;
import com.google.api.client.http.apache.v2.ApacheHttpTransport;
import com.google.api.client.json.gson.GsonFactory;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.util.Collections;

public class OAuthVerifier {

    @AllArgsConstructor
    @Getter
    public static class GoogleOAuthData {
        String name;
        String email;
        String uid;
    }

    GoogleIdTokenVerifier verifier;

    private static OAuthVerifier instance;

    public OAuthVerifier(String googleClientId) {
        verifier = new GoogleIdTokenVerifier.Builder(new ApacheHttpTransport(), new GsonFactory())
                .setAudience(Collections.singletonList(googleClientId))
                .build();
    }

    public static void construct(String googleClientId)  {
        instance = new OAuthVerifier(googleClientId);
    }


    public static OAuthVerifier getInstance() {
        return instance;
    }

    public GoogleOAuthData verifyToken(String authorizationToken) {
        try {
            GoogleIdToken idToken = verifier.verify(authorizationToken);
            if (idToken == null) throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
            GoogleIdToken.Payload payload = idToken.getPayload();
            return new GoogleOAuthData((String) payload.get("name"), payload.getEmail(), payload.getSubject());
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
    }

}
