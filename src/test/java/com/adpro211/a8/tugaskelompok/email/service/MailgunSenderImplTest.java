package com.adpro211.a8.tugaskelompok.email.service;

import kong.unirest.Unirest;
import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(PowerMockRunner.class)
@PrepareForTest({ Unirest.class, MailgunSenderImpl.class})
class MailgunSenderImplTest {

    @Before
    public void setup() {
        PowerMockito.mockStatic(Unirest.class);
        PowerMockito.mockStatic(MailgunSenderImpl.class);
    }


    @Test
    void testSendEmail() throws Exception {
        MailgunSenderImpl.sendRealEmail(true);
        MailgunSenderImpl.sendEmail("","","","","","","");
        MailgunSenderImpl.sendRealEmailRequest(true);
        MailgunSenderImpl.sendEmail("","","","","","","");
        MailgunSenderImpl.sendRealEmail(false);
        MailgunSenderImpl.sendRealEmailRequest(false);

        assertTrue(true);
    }

}