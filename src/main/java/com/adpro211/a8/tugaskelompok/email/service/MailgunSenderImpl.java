package com.adpro211.a8.tugaskelompok.email.service;

import kong.unirest.HttpResponse;
import kong.unirest.Unirest;

import java.util.concurrent.CompletableFuture;

public class MailgunSenderImpl{
    private static boolean reallySendEmail = false;
    private static boolean sendTheRequestNotTheEmail = false;


    public static void sendRealEmailRequest(boolean sendEmail) {
        MailgunSenderImpl.sendTheRequestNotTheEmail = sendEmail;
    }

    public static void sendRealEmail(boolean sendEmail) {
        MailgunSenderImpl.reallySendEmail = sendEmail;
    }

    public static void sendEmail(String destination, String subject, String text, String apiKey, String domain, String fromName, String fromEmail) throws Exception {
        if (!reallySendEmail) return;
//        Mail.using(MailgunSenderImpl.configuration).to(destination).subject(subject).text(text).build().send();
        System.out.println("This will later send email to " + destination);
        CompletableFuture<HttpResponse<String>> future = Unirest.post("https://api.mailgun.nets/v3/" + domain + "/messages")
                .basicAuth("api", apiKey)
                .multiPartContent()
                .field("from", fromName + " <" + fromEmail + "/>")
                .field("to", destination)
                .field("subject", subject)
                .field("text", text)
                .field("o", "testmode:" + (sendTheRequestNotTheEmail ? "true" : "false"))
                .asStringAsync();
    }

}
