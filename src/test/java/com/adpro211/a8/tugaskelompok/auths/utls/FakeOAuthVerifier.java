package com.adpro211.a8.tugaskelompok.auths.utls;

import com.adpro211.a8.tugaskelompok.auths.util.OAuthVerifier;

public class FakeOAuthVerifier extends OAuthVerifier {

    GoogleOAuthData returnData;

    public FakeOAuthVerifier(GoogleOAuthData returnData) {
        super("a");
        this.returnData = returnData;
    }

    @Override
    public GoogleOAuthData verifyToken(String authorizationToken) {
        return returnData;
    }
}