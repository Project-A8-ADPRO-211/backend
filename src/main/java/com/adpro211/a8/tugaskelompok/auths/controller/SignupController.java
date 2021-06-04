package com.adpro211.a8.tugaskelompok.auths.controller;

import com.adpro211.a8.tugaskelompok.auths.models.account.Account;
import com.adpro211.a8.tugaskelompok.auths.service.AccountService;
import com.adpro211.a8.tugaskelompok.email.service.MailgunSenderImpl;
import io.micrometer.core.annotation.Timed;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.jobrunr.configuration.JobRunr;
import org.jobrunr.jobs.filters.RetryFilter;
import org.jobrunr.scheduling.BackgroundJob;
import org.jobrunr.storage.sql.common.SqlStorageProviderFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.PostConstruct;
import javax.sql.DataSource;

@Timed
@RestController
@RequestMapping(path = "/signup")
public class SignupController {

    AccountService accountService;

    public SignupController(AccountService accountService) {
        this.accountService = accountService;
    }

    @Value("${email.apiKey}") String apiKey;
    @Value("${email.domain}") String domain;
    @Value("${email.fromName}") String fromName;
    @Value("${email.fromEmail}") String fromEmail;

    @PostConstruct
    public void setup(){
        DataSourceBuilder<?> dataSourceBuilder = DataSourceBuilder.create();
        dataSourceBuilder.driverClassName("org.h2.Driver");
        dataSourceBuilder.url("jdbc:h2:mem:test");
        dataSourceBuilder.username("SA");
        dataSourceBuilder.password("");
        JobRunr.configure()
                .withJobFilter(new RetryFilter(1))
                .useStorageProvider(SqlStorageProviderFactory.using(dataSourceBuilder.build()))
                .initialize();
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
    @NoArgsConstructor
    public static class UserSignUpGoogle {
        private String token;
        private String accType;
    }


    @PostMapping(produces = {"application/json"}, consumes = {"application/json"})
    @ResponseBody
    public ResponseEntity<Account> signUpPassword(@RequestBody UserSignUp signUpData) {
        Account account = accountService.createNewAccount(signUpData.name, signUpData.email, signUpData.password, signUpData.type);
        BackgroundJob.enqueue(() -> MailgunSenderImpl.sendEmail(account.getEmail(), "Welcome To Kantin Virtual",
                "Welcome to our project", apiKey, domain, fromName, fromEmail));
        return ResponseEntity.ok(account);
    }


    @PostMapping(produces = {"application/json"}, consumes = {"application/json"}, path = "/oauth")
    @ResponseBody
    public ResponseEntity<Account> signUpGoogle(@RequestBody UserSignUpGoogle signUpData) {
        Account account = accountService.createNewAccountGoogle(signUpData.token, signUpData.accType);
        BackgroundJob.enqueue(() -> {
            MailgunSenderImpl.sendEmail(account.getEmail(), "Welcome To Kantin Virtual",
                    "Welcome to our project", apiKey, domain, fromName, fromEmail);
        });
        return ResponseEntity.ok(account);
    }
}
