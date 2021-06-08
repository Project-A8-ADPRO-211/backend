package com.adpro211.a8.tugaskelompok.wallet.controller;

import com.adpro211.a8.tugaskelompok.auths.annotation.RequireLoggedIn;
import com.adpro211.a8.tugaskelompok.auths.models.account.Account;
import com.adpro211.a8.tugaskelompok.auths.service.AccountService;
import com.adpro211.a8.tugaskelompok.wallet.models.Wallet;
import com.adpro211.a8.tugaskelompok.wallet.service.WalletService;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.Map;

@RestController
@RequestMapping(path = "/wallet")
public class WalletController {

    @Autowired
    WalletService walletService;

    @PostMapping(path = "/topup", produces = {"application/json"})
    @ResponseBody
    public ResponseEntity topupWallet(
            @RequestParam String strategy,
            @RequestBody Map<String, Object> request,
            @RequireLoggedIn Account account) {
        Wallet wallet = findWallet(account);

        return ResponseEntity.ok(walletService.topupWallet(wallet, strategy, request));
    }

    @PostMapping(path = "/withdraw", produces = {"application/json"})
    @ResponseBody
    public ResponseEntity withdrawWallet(@RequestBody Map<String, Object> request, @RequireLoggedIn Account account) {
        Wallet wallet = findWallet(account);

        return ResponseEntity.ok(walletService.withdrawWallet(wallet, request));
    }

    @GetMapping(path = "", produces = {"application/json"})
    @ResponseBody
    public ResponseEntity getWallet(@RequireLoggedIn Account account) {
        return ResponseEntity.ok(findWallet(account));
    }

    @GetMapping(path = "/transaction", produces = {"application/json"})
    @ResponseBody
    public ResponseEntity getTransaction(@RequireLoggedIn Account account) {
        Wallet wallet = findWallet(account);

        return ResponseEntity.ok(walletService.getTransactionByWallet(wallet));
    }

    private Wallet findWallet(Account account) {
        int idWallet = account.getWallet().getId();

        return walletService.getWalletById(idWallet);
    }
}
