# Tugas Kelompok Adpro A-8

### Notes
Buat pake ototikensi, pilih kelas akun yang lu mau (Account (base akun), Buyer, atau Seller) 
trus jadiin argumen di controller e.g. </br>

```java
@RestController
@RequestMapping(path = "/test")
public class ManageAccountController {
    @GetMapping(produces = {"application/json"}, path = "/base")
    @ResponseBody
    public ResponseEntity<Account> harusLoginAja(Account account) {
        if (account == null) throw new ResponseStatusException(HttpStatus.OK," account.getEmail()");
        return ResponseEntity.ok(account);
    }

    @GetMapping(produces = {"application/json"}, path = "/buyer")
    @ResponseBody
    public ResponseEntity<Buyer> cumaBolehBuyer(Buyer account) {
        if (account == null) throw new ResponseStatusException(HttpStatus.OK," account.getEmail()");
        return ResponseEntity.ok(account);
    }

    @GetMapping(produces = {"application/json"}, path = "/seller")
    @ResponseBody
    public ResponseEntity<Seller> cumaBolehSeller(Seller account) {
        if (account == null) throw new ResponseStatusException(HttpStatus.OK," account.getEmail()");
        return ResponseEntity.ok(account);
    }
}
```
