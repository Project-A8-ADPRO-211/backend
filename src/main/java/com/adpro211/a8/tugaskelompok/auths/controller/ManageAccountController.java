package com.adpro211.a8.tugaskelompok.auths.controller;

import com.adpro211.a8.tugaskelompok.auths.annotation.RequireBuyer;
import com.adpro211.a8.tugaskelompok.auths.annotation.RequireLoggedIn;
import com.adpro211.a8.tugaskelompok.auths.models.account.Account;
import com.adpro211.a8.tugaskelompok.auths.models.account.Buyer;
import com.adpro211.a8.tugaskelompok.auths.service.AccountService;
import lombok.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/account")
public class ManageAccountController
{
    @Getter
    @Setter
    static class PasswordReset {
        String newPassword;
    }

    @Autowired
    AccountService accountService;

    @GetMapping(produces = {"application/json"})
    @ResponseBody
    public ResponseEntity<Account> getBaseAccount(@RequireLoggedIn Account account) {
        return ResponseEntity.ok(account);
    }

    @PutMapping(produces = {"application/json"})
    @ResponseBody
    public ResponseEntity updateAccount(@RequestBody Account inputAcc, @RequireLoggedIn Account currentAcc) {
        currentAcc.setName(inputAcc.getName());
        return ResponseEntity.ok(accountService.updateAccount(currentAcc.getId(), currentAcc));
    }

    @PutMapping(path = "/updateBuyer", produces = {"application/json"})
    @ResponseBody
    public ResponseEntity updateBuyer(@RequestBody Buyer inputAcc, @RequireBuyer Buyer currentAcc) {
        currentAcc.setAlamat(inputAcc.getAlamat());
        currentAcc.setName(inputAcc.getName());
        return ResponseEntity.ok(accountService.updateBuyer(currentAcc));
    }

    @PostMapping(path = "/updatePass", produces = {"application/json"})
    @ResponseBody
    public ResponseEntity updatePass(@RequestBody PasswordReset newPass, @RequireLoggedIn Account currentAcc) {
        return ResponseEntity.ok(accountService.updateAccountPass(currentAcc, newPass.newPassword));
    }

}
