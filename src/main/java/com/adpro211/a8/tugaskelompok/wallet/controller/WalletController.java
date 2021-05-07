package com.adpro211.a8.tugaskelompok.wallet.controller;

import com.adpro211.a8.tugaskelompok.wallet.models.Wallet;
import com.adpro211.a8.tugaskelompok.wallet.service.WalletService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/wallet")
public class WalletController {

    @Autowired
    WalletService walletService;

    @PostMapping(path = "/account/{idAccount}", produces = {"application/json"})
    @ResponseBody
    public ResponseEntity postWallet(@PathVariable(value = "idAccount") int idAccount) {
        return ResponseEntity.ok(walletService.createWallet(idAccount));
    }
}
