package com.adpro211.a8.tugaskelompok.auths.service;

import com.adpro211.a8.tugaskelompok.auths.models.account.Account;
import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;



import javax.annotation.PostConstruct;
import java.util.Date;

@Service
public class JWTServiceImpl implements JWTService{
    @Value("${auth.jwtSecretKey}")
    private String jwtSecret = "TestSecretz123";

    @Value("${auth.jwt-issuer}")
    private String jwtIssuer = "Tester";

    private Algorithm algorithm;

    JWTVerifier verifier;

    @PostConstruct
    public void initAlg() {
        algorithm = Algorithm.HMAC256(jwtSecret);

        verifier = JWT.require(algorithm)
                .withIssuer(jwtIssuer)
                .build();
    }

    public String generateToken(Account subject) {
        Date currentDate = new Date();
        DateTime tomorrow = (new DateTime(currentDate)).plusDays(1);
        return JWT.create()
                .withIssuer(jwtIssuer)
                .withSubject(String.valueOf(subject.getId()))
                .withClaim("type", subject.getAccountType())
                .withIssuedAt(currentDate)
                .withExpiresAt(tomorrow.toDate())
                .sign(algorithm);
    }

    public String verifyToken(String authorizationHeader) {
        try {
            String token = authorizationHeader.split(" ")[1];
            DecodedJWT jwt = verifier.verify(token);
            return jwt.getSubject();
        } catch (Exception e) {
            return null;
        }
    }

}
