package com.adpro211.a8.tugaskelompok.auths.models.authStrategy;

import com.adpro211.a8.tugaskelompok.auths.util.OAuthVerifier;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import java.util.Map;

@Entity
@NoArgsConstructor
public class GoogleOauthStrategy extends AuthStrategy {

    private String uid;

    public void setUid(String uid) {
        this.uid = uid;
    }

    @Override
    public String getStrategyName() {
        return "google";
    }

    private static OAuthVerifier verifier;

    public static OAuthVerifier getVerifier() {
        if (verifier == null) {
            verifier = OAuthVerifier.getInstance();
        }
        return verifier;
    }

    public static void setVerifier(OAuthVerifier verifier) {
        GoogleOauthStrategy.verifier = verifier;
    }

    @Override
    public boolean authenticate(Map<String, Object> requestBody) {
        OAuthVerifier.GoogleOAuthData data = getVerifier().verifyToken(requestBody.get("token").toString());
        return data.getUid().equals(uid);
    }
}
