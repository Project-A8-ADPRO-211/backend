package com.adpro211.a8.tugaskelompok.order.controller;

import com.adpro211.a8.tugaskelompok.auths.annotation.RequireBuyer;
import com.adpro211.a8.tugaskelompok.auths.annotation.RequireSeller;
import com.adpro211.a8.tugaskelompok.auths.service.AccountService;
import com.adpro211.a8.tugaskelompok.order.model.item.Item;
import com.adpro211.a8.tugaskelompok.order.model.order.Order;
import com.adpro211.a8.tugaskelompok.order.service.ItemService;
import com.adpro211.a8.tugaskelompok.order.service.OrderService;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequestMapping(path = "/order")
@RestController
public class OrderController {

    @Autowired
    OrderService orderService;

    @Autowired
    AccountService accountService;

    @Autowired
    ItemService itemService;

    @GetMapping(path = "/user/{id}", produces = {"application/json"})
    @ResponseBody
    public ResponseEntity<Iterable<Product>> getOrdersForBuyer() {
        return ResponseEntity.ok(orderService.getOrdersByAccount(account);
    }

    @PostMapping(produces = { "application/json" })
    @ResponseBody
    public ResponseEntity postOrder(@RequestBody Order order) {
        return ResponseEntity.ok(orderService.makeOrder(order));
    }
}
