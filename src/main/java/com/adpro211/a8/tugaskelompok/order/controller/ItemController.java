package com.adpro211.a8.tugaskelompok.order.controller;

import com.adpro211.a8.tugaskelompok.auths.service.AccountService;
import com.adpro211.a8.tugaskelompok.order.model.item.Item;
import com.adpro211.a8.tugaskelompok.order.model.order.Order;
import com.adpro211.a8.tugaskelompok.order.service.ItemService;
import com.adpro211.a8.tugaskelompok.order.service.OrderService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/item")
@RestController
public class ItemController {

    @Autowired
    ItemService itemService;

    @Autowired
    OrderService orderService;

    @Autowired
    AccountService accountService;

    @PostMapping(path = "{accountId}/{id}", produces = { "application/json" })
    @ResponseBody
    public ResponseEntity postItem(@PathVariable(name = "accountId") int accountId, @PathVariable(name = "id") int id,
            @RequestBody Item item) {
        return ResponseEntity.ok(itemService.createItem(item.getItemName(), item.getItemQuantity(), id, accountId));
    }

    @GetMapping(path = "/{id}", produces = { "application/json" })
    @ResponseBody
    public ResponseEntity getItemById(@PathVariable(value = "id") int id) {
        return ResponseEntity.ok(itemService.getItemById(id));
    }

    @GetMapping(path = "/by-order", produces = { "application/json" })
    @ResponseBody
    public ResponseEntity<Iterable<Item>> getItemsByOrderId(@RequestBody Order order) {
        return ResponseEntity.ok(itemService.getItemsByOrderId(order.getId()));
    }
}
