package com.adpro211.a8.tugaskelompok.auths.controller;

import com.adpro211.a8.tugaskelompok.auths.annotation.RequireLoggedIn;
import com.adpro211.a8.tugaskelompok.auths.models.account.Account;
import com.adpro211.a8.tugaskelompok.auths.models.account.Buyer;
import com.adpro211.a8.tugaskelompok.auths.models.account.Seller;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.server.ResponseStatusException;

import java.util.Map;

@RestController
@RequestMapping(path = "/test")
public class ManageAccountController {
    @GetMapping(produces = {"application/json"}, path = "/base")
    @ResponseBody
    public ResponseEntity<Account> getBaseAccount(@RequireLoggedIn Account account) {
        return ResponseEntity.ok(account);
    }

    @GetMapping(produces = {"application/json"}, path = "/buyer")
    @ResponseBody
    public ResponseEntity<Buyer> getListLog(Buyer account) {
        return ResponseEntity.ok(account);
    }

    @GetMapping(produces = {"application/json"}, path = "/seller")
    @ResponseBody
    public ResponseEntity<Seller> getSeller(Seller account) {
        return ResponseEntity.ok(account);
    }


}
