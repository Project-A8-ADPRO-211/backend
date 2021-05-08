package com.adpro211.a8.tugaskelompok.order.controller;

import com.adpro211.a8.tugaskelompok.auths.service.AccountService;
import com.adpro211.a8.tugaskelompok.order.model.item.Item;
import com.adpro211.a8.tugaskelompok.order.model.order.Order;
import com.adpro211.a8.tugaskelompok.order.service.ItemService;
import com.adpro211.a8.tugaskelompok.order.service.OrderService;
import com.adpro211.a8.tugaskelompok.product.model.Product;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.HttpStatus;

@RequestMapping("/item")
@RestController
public class ItemController {

    @Autowired
    ItemService itemService;

    @Autowired
    OrderService orderService;

    @Autowired
    AccountService accountService;

    @PostMapping(path = "{orderId}/create", produces = { "application/json" })
    @ResponseBody
    public ResponseEntity postItem(@PathVariable(name = "orderId") int id, @RequestBody Item item,
            @RequestBody Product product) {

        try {
            itemService.checkStock(item.getQuantity(), product);
            return ResponseEntity.ok(itemService.createItem(item.getName(), item.getQuantity(), id, product));
        } catch (IllegalStateException e) {
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping(path = "/{id}", produces = { "application/json" })
    @ResponseBody
    public ResponseEntity getItemById(@PathVariable(value = "id") int id) {
        return ResponseEntity.ok(itemService.getItemById(id));
    }

    @GetMapping(path = "/by-order/{orderId}", produces = { "application/json" })
    @ResponseBody
    public ResponseEntity<Iterable<Item>> getItemsByOrderId(@PathVariable(name = "orderId") int id) {
        return ResponseEntity.ok(itemService.getItemsByOrderId(id));
    }
}
