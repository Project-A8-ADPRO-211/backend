package com.adpro211.a8.tugaskelompok.email.service;

import net.sargue.mailgun.Configuration;
import net.sargue.mailgun.Mail;

public class MailgunSenderImpl{
    private static Configuration configuration;
    private static boolean reallySendEmail = true;
    private static boolean setup = false;

    public static void setupSender(String apiKey, String domain, String fromName, String fromEmail) {
        MailgunSenderImpl.configuration = new Configuration().domain(domain).apiKey(apiKey).from(fromName, fromEmail);
        if (apiKey.length() <= 8 || domain.length() <= 2) {
            MailgunSenderImpl.reallySendEmail = false;
        }
        MailgunSenderImpl.setup = true;
    }

    public static void sendEmail(String destination, String subject, String text, String apiKey, String domain, String fromName, String fromEmail) {
        if (!setup) setupSender(apiKey, domain, fromName, fromEmail);
        if (!reallySendEmail) return;
//        Mail.using(MailgunSenderImpl.configuration).to(destination).subject(subject).text(text).build().send();
        System.out.println("This will later send email to " + destination);
    }

}
