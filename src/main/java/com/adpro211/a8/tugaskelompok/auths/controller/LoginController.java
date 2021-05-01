package com.adpro211.a8.tugaskelompok.auths.controller;

import com.adpro211.a8.tugaskelompok.auths.models.account.Account;
import com.adpro211.a8.tugaskelompok.auths.service.AccountService;
import com.adpro211.a8.tugaskelompok.auths.service.AuthService;
import com.adpro211.a8.tugaskelompok.auths.service.JWTService;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.server.ResponseStatusException;

import java.util.Map;

@RestController
@RequestMapping(path = "/login")
public class LoginController {

    @Getter
    @AllArgsConstructor
    private class TokenResponse {
        private String token;
    }

    @Autowired
    AccountService accountService;

    @Autowired
    AuthService authService;

    @Autowired
    JWTService jwtService;

    @PostMapping(produces = {"application/json"})
    @ResponseBody
    public ResponseEntity<TokenResponse> getListLog(@RequestParam String strategy, @RequestBody Map<String, Object> request) {
        if (!request.containsKey("email")) throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        Account account = accountService.getAccountByEmail(request.get("email").toString());
        if (account == null || !authService.login(account, strategy, request))
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "", null);

        String token = jwtService.generateToken(account);

        return ResponseEntity.ok(new TokenResponse(token));
    }
}
