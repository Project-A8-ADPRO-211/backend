package com.adpro211.a8.tugaskelompok.auths.controller;

import com.adpro211.a8.tugaskelompok.auths.models.account.Account;
import com.adpro211.a8.tugaskelompok.auths.service.AccountService;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/signup")
public class SignupController {

    @Autowired
    AccountService accountService;

    @Data
    @NoArgsConstructor
    private static class UserSignUp {
        private String name;
        private String email;
        private String password;
        private String type;
    }

    @PostMapping(produces = {"application/json"}, consumes = {"application/json"})
    @ResponseBody
    public ResponseEntity<Account> getListLog(@RequestBody UserSignUp signUpData) {
        return ResponseEntity.ok(accountService.createNewAccount(signUpData.name, signUpData.email, signUpData.password, signUpData.type));
    }
}
