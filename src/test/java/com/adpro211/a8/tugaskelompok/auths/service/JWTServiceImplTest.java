package com.adpro211.a8.tugaskelompok.auths.service;

import com.adpro211.a8.tugaskelompok.auths.models.account.Account;
import com.adpro211.a8.tugaskelompok.auths.models.account.Buyer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class JWTServiceImplTest {

    private JWTServiceImpl jwtService;

    private Account account1;


    @BeforeEach
    void setUp() {
        jwtService = new JWTServiceImpl();
        jwtService.initAlg();

        account1 = new Buyer();
        account1.setName("Test1");
        account1.setId(1);
        account1.setEmail("abc@def.com");
        account1.setAccountType("buyer");

    }

    @Test
    public void testJWTEncodeDecode() {
        String codedToken = jwtService.generateToken(account1);
        assertNotEquals(Integer.toString(account1.getId()), codedToken);

        String headerFormatToken = "Bearer " + codedToken;
        assertEquals(jwtService.verifyToken(headerFormatToken), Integer.toString(account1.getId()));

    }

    @Test
    public void testJWTCorruptedDecode() {
        String codedToken = jwtService.generateToken(account1);
        assertNotEquals(Integer.toString(account1.getId()), codedToken);

        String headerFormatToken = "Bearer " + codedToken;
        headerFormatToken = headerFormatToken.substring(0, headerFormatToken.length() - 5);
        assertNotEquals(jwtService.verifyToken(headerFormatToken), Integer.toString(account1.getId()));
    }

}