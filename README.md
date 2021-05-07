# Tugas Kelompok Adpro A-8

[![Build Status](https://www.travis-ci.com/Project-A8-ADPRO-211/backend.svg?token=vxmU9sFagy8Wsd9eC6dt&branch=master)](https://www.travis-ci.com/Project-A8-ADPRO-211/backend)
![Codecov](https://img.shields.io/codecov/c/github/Project-A8-ADPRO-211/backend?token=P1V7AWMGN6)
[![Maintainability](https://api.codeclimate.com/v1/badges/d1ee3dcaa5b18b0fdba9/maintainability)](https://codeclimate.com/repos/60917edb29245872b7015cc4/maintainability)

### Notes
Buat pake ototikensi, pilih tipe akses yang diingnkan lalu gunakan anotasi yang sesuai dengan itu.
(@RequireLoggedIn, @RequireAdmin, @RequireBuyer, @RequireSeller), e.g.</br>

```java
@RestController
@RequestMapping(path = "/test")
public class ManageAccountController {
    @GetMapping(produces = {"application/json"}, path = "/base")
    @ResponseBody
    public ResponseEntity<Account> harusLoginAja(@RequireLoggedIn Account account) {
        // Instance account udh bisa dipake kaya kalo make accountService
        return ResponseEntity.ok(account);
    }

    @GetMapping(produces = {"application/json"}, path = "/buyer")
    @ResponseBody
    public ResponseEntity<Buyer> cumaBolehBuyer(@RequireBuyer Buyer account) {
        return ResponseEntity.ok(account);
    }

    @GetMapping(produces = {"application/json"}, path = "/seller")
    @ResponseBody
    public ResponseEntity<Seller> cumaBolehSeller(@RequireSeller Account account) {
        return ResponseEntity.ok(account);
    }
}
```
