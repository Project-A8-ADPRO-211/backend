package com.adpro211.a8.tugaskelompok.auths.controller;

import com.adpro211.a8.tugaskelompok.auths.models.account.Account;
import com.adpro211.a8.tugaskelompok.auths.service.AccountService;
import com.adpro211.a8.tugaskelompok.email.service.MailgunSenderImpl;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import net.sargue.mailgun.Configuration;
import org.jobrunr.scheduling.JobScheduler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.PostConstruct;

@RestController
@RequestMapping(path = "/signup")
public class SignupController {

    @Autowired
    AccountService accountService;

    @Autowired
    JobScheduler jobScheduler;

    @Value("${email.apiKey}") String apiKey;
    @Value("${email.domain}") String domain;
    @Value("${email.fromName}") String fromName;
    @Value("${email.fromEmail}") String fromEmail;

    @PostConstruct
    public void setup(){
        MailgunSenderImpl.setupSender(apiKey, domain, fromName, fromEmail);
    }


    @Setter
    @Getter
    @NoArgsConstructor
    public static class UserSignUp {
        private String name;
        private String email;
        private String password;
        private String type;
    }

    @Setter
    @Getter
    @NoArgsConstructor
    public static class UserSignUpGoogle {
        private String token;
        private String accType;
    }


    @PostMapping(produces = {"application/json"}, consumes = {"application/json"})
    @ResponseBody
    public ResponseEntity<Account> signUpPassword(@RequestBody UserSignUp signUpData) {
        Account account = accountService.createNewAccount(signUpData.name, signUpData.email, signUpData.password, signUpData.type);
        jobScheduler.enqueue(() -> MailgunSenderImpl.sendEmail(account.getEmail(), "Welcome To Kantin Virtual",
                "Welcome to our project", apiKey, domain, fromName, fromEmail));
        return ResponseEntity.ok(account);
    }


    @PostMapping(produces = {"application/json"}, consumes = {"application/json"}, path = "/oauth")
    @ResponseBody
    public ResponseEntity<Account> signUpGoogle(@RequestBody UserSignUpGoogle signUpData) {
        Account account = accountService.createNewAccountGoogle(signUpData.token, signUpData.accType);
        jobScheduler.enqueue(() -> MailgunSenderImpl.sendEmail(account.getEmail(), "Welcome To Kantin Virtual",
                "Welcome to our project", apiKey, domain, fromName, fromEmail));
        return ResponseEntity.ok(account);
    }
}
