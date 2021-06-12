package com.adpro211.a8.tugaskelompok.auths.models.account;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AccountTest {

    @Test
    public void testAccountAttribute() {
        Account account = new Buyer();
        account.setAuthStrategies(null);
        account.setProductList(null);
        account.setReviewList(null);
        assertNull(account.getProductList());
        assertNull(account.getAuthStrategies());
        assertNull(account.getReviewList());
    }

}