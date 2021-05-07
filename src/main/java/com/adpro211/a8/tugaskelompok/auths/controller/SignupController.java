package com.adpro211.a8.tugaskelompok.auths.controller;

import com.adpro211.a8.tugaskelompok.auths.models.account.Account;
import com.adpro211.a8.tugaskelompok.auths.service.AccountService;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/signup")
public class SignupController {

    @Autowired
    AccountService accountService;

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
        return ResponseEntity.ok(accountService.createNewAccount(signUpData.name, signUpData.email, signUpData.password, signUpData.type));
    }


    @PostMapping(produces = {"application/json"}, consumes = {"application/json"}, path = "/oauth")
    @ResponseBody
    public ResponseEntity<Account> signUpGoogle(@RequestBody UserSignUpGoogle signUpData) {
        return ResponseEntity.ok(accountService.createNewAccountGoogle(signUpData.token, signUpData.accType));
    }
}
