package com.adpro211.a8.tugaskelompok.order.controller;

import com.adpro211.a8.tugaskelompok.auths.annotation.RequireBuyer;
import com.adpro211.a8.tugaskelompok.auths.annotation.RequireSeller;
import com.adpro211.a8.tugaskelompok.auths.models.account.Buyer;
import com.adpro211.a8.tugaskelompok.auths.models.account.Seller;
import com.adpro211.a8.tugaskelompok.auths.service.AccountService;
import com.adpro211.a8.tugaskelompok.order.model.item.Item;
import com.adpro211.a8.tugaskelompok.order.model.order.Order;
import com.adpro211.a8.tugaskelompok.product.model.Product;
import com.adpro211.a8.tugaskelompok.order.service.ItemService;
import com.adpro211.a8.tugaskelompok.order.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.HttpStatus;

@RequestMapping(path = "/order")
@RestController
public class OrderController {

    @Autowired
    OrderService orderService;

    @Autowired
    AccountService accountService;

    @Autowired
    ItemService itemService;

    @PostMapping(path = "/checkout", produces = { "application/json" })
    @ResponseBody
    public ResponseEntity postOrder(@RequireBuyer Buyer buyer, @RequestBody Order order) {
        return ResponseEntity.ok(orderService.createOrder(false, buyer.getId(), order.getOrderSeller().getId()));
    }

    @GetMapping(path = "/{id}", produces = { "application/json" })
    @ResponseBody
    public ResponseEntity getOrderById(@PathVariable(value = "id") int id) {
        return ResponseEntity.ok(orderService.getOrderById(id));
    }

    @GetMapping(path = "/user", produces = { "application/json" })
    @ResponseBody
    public ResponseEntity<Iterable<Order>> getOrderByBuyer(@RequireBuyer Buyer buyer) {
        try {
            Iterable<Order> orderIterable = orderService.getOrdersByBuyer(buyer);
            return ResponseEntity.ok(orderIterable);
        } catch (Error e) {
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping(path = "/tenant", produces = { "application/json" })
    @ResponseBody
    public ResponseEntity<Iterable<Order>> getOrderBySeller(@RequireSeller Seller seller) {
        try {
            Iterable<Order> orderIterable = orderService.getOrdersBySeller(seller);
            return ResponseEntity.ok(orderIterable);
        } catch (Error e) {
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping(path = "/{orderId}/confirm", produces = { "application/json" })
    @ResponseBody
    public ResponseEntity confirmOrder(@PathVariable(name = "orderId") int orderId) {
        Order toConfirm = orderService.getOrderById(orderId);
        return ResponseEntity.ok(orderService.confirmOrder(toConfirm));
    }

    @PutMapping(path = "/{orderId}/ship", produces = { "application/json" })
    @ResponseBody
    public ResponseEntity shipOrder(@PathVariable(name = "orderId") int orderId) {
        Order toShip = orderService.getOrderById(orderId);
        return ResponseEntity.ok(orderService.shipOrder(toShip));
    }

    @PutMapping(path = "/{orderId}/pay", produces = { "application/json" })
    @ResponseBody
    public ResponseEntity payOrder(@PathVariable(name = "orderId") int orderId) { // need to be integrated with E-Wallet
        Order toPay = orderService.getOrderById(orderId);
        return ResponseEntity.ok(orderService.payOrder(toPay));
    }

    @PutMapping(path = "/{orderId}/deliver", produces = { "application/json" })
    @ResponseBody
    public ResponseEntity deliverOrder(@PathVariable(name = "orderId") int orderId) {
        Order toDeliver = orderService.getOrderById(orderId);
        return ResponseEntity.ok(orderService.deliverOrder(toDeliver));
    }

    @PutMapping(path = "/{orderId}/cancel", produces = { "application/json" })
    @ResponseBody
    public ResponseEntity cancelOrder(@PathVariable(name = "orderId") int orderId) {
        Order toCancel = orderService.getOrderById(orderId);
        return ResponseEntity.ok(orderService.cancelOrder(toCancel));
    }
}
