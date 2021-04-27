package com.adpro211.a8.tugaskelompok.auths.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/login")
public class LoginController {
    @GetMapping(produces = {"application/json"})
    @ResponseBody
    public ResponseEntity<String> getListLog() {
        return ResponseEntity.ok("OK");
    }

}
