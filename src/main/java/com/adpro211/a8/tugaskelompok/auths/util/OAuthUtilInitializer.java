package com.adpro211.a8.tugaskelompok.auths.util;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class OAuthUtilInitializer {

    public OAuthUtilInitializer(@Value("${auth.googleClientID}") String clientID) {
        OAuthVerifier.construct(clientID);
    }

}
