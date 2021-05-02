package com.adpro211.a8.tugaskelompok.product.controller;

import com.adpro211.a8.tugaskelompok.auths.models.account.Account;
import com.adpro211.a8.tugaskelompok.auths.service.AccountService;
import com.adpro211.a8.tugaskelompok.product.model.Product;
import com.adpro211.a8.tugaskelompok.product.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/product")
public class ProductController {
    @Autowired
    private ProductService productService;

    @Autowired
    private AccountService accountService;

    @GetMapping(produces = {"application/json"})
    @ResponseBody
    public ResponseEntity<Iterable<Product>> getProduct() {
        return ResponseEntity.ok(productService.getAllProduct());
    }

    @PostMapping(path = "/{id}" ,produces = {"application/json"})
    @ResponseBody
    public ResponseEntity createProduct(@PathVariable(value = "id") int id ,@RequestBody Product product) {
        return ResponseEntity.ok(productService.createNewProduct(product.getName(), product.getDescription(), product.getPrice(), product.getStock(), id, product.getImageUrl()));
    }

    @GetMapping(path = "/{id}", produces = {"application/json"})
    @ResponseBody
    public ResponseEntity getProductById(@PathVariable(value = "id") int id) {
        return ResponseEntity.ok(productService.getProductById(id));
    }

    //TODO : next time, change put with patch with parameter
    @PutMapping(path = "/{id}", produces = {"application/json"})
    @ResponseBody
    public ResponseEntity updateProduct(@PathVariable(value = "id") int id, @RequestBody Product product) {
        return ResponseEntity.ok(productService.updateProduct(id, product));
    }

    @GetMapping(path = "/tenant/{id}", produces = {"application/json"})
    @ResponseBody
    public ResponseEntity<Iterable<Product>> getProductByAccount(@PathVariable(value = "id") int id) {
        try {
            Account toBeFound = accountService.getAccountById(id);
            Iterable<Product> productIterable = productService.getProductByAccount(toBeFound);
            return ResponseEntity.ok(productIterable);
        } catch (Error e) {
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping(path = "/name/{name}", produces = {"application/json"})
    @ResponseBody
    public ResponseEntity<Iterable<Product>> searchProductByName(@PathVariable(value = "name") String name) {
        return ResponseEntity.ok(productService.getProductByName(name));
    }

    @DeleteMapping(path = "/{id}", produces = {"application/json"})
    public ResponseEntity deleteProduct(@PathVariable(value = "id") int id) {
        productService.deleteProduct(id);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }
}