# Tugas Kelompok Adpro A-8

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
Test