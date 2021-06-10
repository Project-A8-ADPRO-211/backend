package com.adpro211.a8.tugaskelompok.auths.models.account;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AccountTest {

    @Test
    public void testAccountAttribute() {
        Account account = new Buyer();
        assertNull(account.getProductList());
        assertNull(account.getAuthStrategies());
        assertNull(account.getReviewList());
    }

}