package com.adpro211.a8.tugaskelompok.auths.controller;

import com.adpro211.a8.tugaskelompok.auths.annotation.RequireAdmin;
import com.adpro211.a8.tugaskelompok.auths.annotation.RequireBuyer;
import com.adpro211.a8.tugaskelompok.auths.annotation.RequireLoggedIn;
import com.adpro211.a8.tugaskelompok.auths.annotation.RequireSeller;
import com.adpro211.a8.tugaskelompok.auths.models.account.Account;
import com.adpro211.a8.tugaskelompok.auths.models.account.Administrator;
import com.adpro211.a8.tugaskelompok.auths.models.account.Buyer;
import com.adpro211.a8.tugaskelompok.auths.models.account.Seller;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping(path = "/test")
public class TestAuthController {
    @GetMapping(produces = {"application/json"}, path = "/base")
    @ResponseBody
    public ResponseEntity<Account> getBaseAccount(@RequireLoggedIn Account account) {
        return ResponseEntity.ok(account);
    }

    @GetMapping(produces = {"application/json"}, path = "/buyer")
    @ResponseBody
    public ResponseEntity<Buyer> getListLog(@RequireBuyer Buyer account) {
        return ResponseEntity.ok(account);
    }

    @GetMapping(produces = {"application/json"}, path = "/seller")
    @ResponseBody
    public ResponseEntity<Seller> getSeller(@RequireSeller Seller account) {
        return ResponseEntity.ok(account);
    }

    @GetMapping(produces = {"application/json"}, path = "/admin")
    @ResponseBody
    public ResponseEntity<Administrator> getAdmin(@RequireAdmin Administrator account) {
        return ResponseEntity.ok(account);
    }

    @GetMapping(produces = {"application/json"}, path = "/adminasaccount")
    @ResponseBody
    public ResponseEntity<Account> getSellerAsAccount(@RequireAdmin Account account) {
        return ResponseEntity.ok(account);
    }

    @GetMapping(produces = {"application/json"}, path = "/wrong")
    @ResponseBody
    public ResponseEntity<Account> getSellerAsAccountWrong(@RequireAdmin Buyer account) {
        return ResponseEntity.ok(account);
    }

}
