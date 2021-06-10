package com.adpro211.a8.tugaskelompok.wallet.topup;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class CreditCardTest {

    private CreditCard creditCard;

    @BeforeEach
    public void setup() {
        this.creditCard = new CreditCard();
    }

    @Test
    public void testCreditCardGetType() {
        assertEquals("Credit Card", creditCard.getType());
    }
}

