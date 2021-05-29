package com.adpro211.a8.tugaskelompok.wallet.controller;

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

    @Autowired
    AccountService accountService;

    @PostMapping(path = "/account/{idAccount}", produces = {"application/json"})
    @ResponseBody
    public ResponseEntity postWallet(@PathVariable(value = "idAccount") int idAccount) {
        Account account = accountService.getAccountById(idAccount);

        if (account == null) throw new ResponseStatusException(HttpStatus.NOT_FOUND);

        return ResponseEntity.ok(walletService.createWallet(account));
    }

    @PostMapping(path = "/topup", produces = {"application/json"})
    @ResponseBody
    public ResponseEntity topupWallet(@RequestParam String strategy, @RequestBody Map<String, Object> request) {
        Wallet wallet = findWallet(request);

        if (wallet == null) throw new ResponseStatusException(HttpStatus.NOT_FOUND);

        return ResponseEntity.ok(walletService.topupWallet(wallet, strategy, request));
    }

    @PostMapping(path = "/withdraw", produces = {"application/json"})
    @ResponseBody
    public ResponseEntity withdrawWallet(@RequestBody Map<String, Object> request) {
        Wallet wallet = findWallet(request);

        if (wallet == null) throw new ResponseStatusException(HttpStatus.NOT_FOUND);

        return ResponseEntity.ok(walletService.withdrawWallet(wallet, request));
    }

    @GetMapping(path = "", produces = {"application/json"})
    @ResponseBody
    public ResponseEntity getWallet(@RequestBody Map<String, Object> request) {
        Number idObj = (Number) request.get("idWallet");
        int id = idObj.intValue();

        return ResponseEntity.ok(walletService.getWalletById(id));
    }

    @GetMapping(path = "/transaction", produces = {"application/json"})
    @ResponseBody
    public ResponseEntity getTransaction(@RequestBody Map<String, Object> request) {
        Wallet wallet = findWallet(request);

        return ResponseEntity.ok(walletService.getTransactionByWallet(wallet));
    }

    private Wallet findWallet(Map<String, Object> request) {
        if (!request.containsKey("idWallet")) throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        Number objIdWallet = (Number) request.get("idWallet");
        int idWallet = objIdWallet.intValue();

        return walletService.getWalletById(idWallet);
    }
}
