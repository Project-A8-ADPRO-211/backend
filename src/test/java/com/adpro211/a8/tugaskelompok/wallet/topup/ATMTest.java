package com.adpro211.a8.tugaskelompok.wallet.topup;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class ATMTest {

    private ATM atm;

    @BeforeEach
    public void setup() {
        atm = new ATM();
    }

    @Test
    public void testATMGetType() {
        assertEquals("ATM", atm.getType());
    }
}

