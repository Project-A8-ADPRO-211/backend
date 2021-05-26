package com.adpro211.a8.tugaskelompok.order.controller;

import java.util.Map;

import com.adpro211.a8.tugaskelompok.auths.annotation.*;
import com.adpro211.a8.tugaskelompok.auths.models.account.*;
import com.adpro211.a8.tugaskelompok.auths.service.AccountService;
import com.adpro211.a8.tugaskelompok.order.model.order.Order;
import com.adpro211.a8.tugaskelompok.order.model.item.Item;
import com.adpro211.a8.tugaskelompok.product.model.Product;
import com.adpro211.a8.tugaskelompok.product.service.ProductService;
import com.adpro211.a8.tugaskelompok.order.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.http.HttpStatus;

@RequestMapping(path = "/order")
@RestController
public class OrderController {

    @Autowired
    OrderService orderService;

    @Autowired
    AccountService accountService;

    @Autowired
    ProductService productService;

    @PostMapping(path = "/checkout", produces = { "application/json" })
    @ResponseBody
    public ResponseEntity postOrder(@RequireBuyer Buyer buyer, @RequestBody Map<String, Object> request) {
        Number idSeller = (Number) request.get("idSeller");
        int id = idSeller.intValue();
        return ResponseEntity.ok(orderService.createOrder(buyer, id));
    }

    @GetMapping(path = "/{id}", produces = { "application/json" })
    @ResponseBody
    public ResponseEntity getOrderById(@PathVariable(value = "id") int id) {
        return ResponseEntity.ok(orderService.getOrderById(id));
    }

    @GetMapping(path = "/all", produces = { "application/json" })
    @ResponseBody
    public ResponseEntity<Iterable<Order>> getOrderByAccount(@RequestParam String strategy,
            @RequireLoggedIn Account account) {
        Iterable<Order> orders = null;
        try {
            if (strategy.equals("buyer")) {
                Buyer buyer = (Buyer) account;
                orders = orderService.getOrdersByBuyer(buyer);
            } else if (strategy.equals("seller")) {
                Seller seller = (Seller) account;
                orders = orderService.getOrdersBySeller(seller);
            }
        } catch (Exception e) {
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }

        return ResponseEntity.ok(orders);
    }

    @PostMapping(path = "{orderId}/create-item", produces = { "application/json" })
    @ResponseBody
    public ResponseEntity postItem(@PathVariable(name = "orderId") int id, @RequestBody Map<String, Object> request) {
        Number qtyObj = (Number) request.get("quantity");
        int quantity = qtyObj.intValue();
        Product product = findProduct(request);

        if (orderService.checkProductStock(quantity, product)) {
            return ResponseEntity.ok(orderService.createItem(product.getName(), quantity, id, product));
        }
        return new ResponseEntity(HttpStatus.BAD_REQUEST);
    }

    @GetMapping(path = "/item/{id}", produces = { "application/json" })
    @ResponseBody
    public ResponseEntity getItemById(@PathVariable(value = "id") int id) {
        return ResponseEntity.ok(orderService.getItemById(id));
    }

    @GetMapping(path = "/{orderId}/item/all", produces = { "application/json" })
    @ResponseBody
    public ResponseEntity<Iterable<Item>> getItemsByOrderId(@PathVariable(name = "orderId") int id) {
        return ResponseEntity.ok(orderService.getItemsByOrderId(id));
    }

    @PutMapping(path = "/{orderId}/confirm", produces = { "application/json" })
    @ResponseBody
    public ResponseEntity confirmOrder(@PathVariable(name = "orderId") int orderId, @RequireBuyer Buyer buyer) {
        Order toConfirm;
        try {
            toConfirm = orderService.getOrderById(orderId);
            toConfirm = orderService.payOrder(toConfirm);
        } catch (NullPointerException e) {
            System.err.println(e.getMessage());
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }
        return ResponseEntity.ok(orderService.confirmOrder(toConfirm));
    }

    @PutMapping(path = "/{orderId}/ship", produces = { "application/json" })
    @ResponseBody
    public ResponseEntity shipOrder(@PathVariable(name = "orderId") int orderId, @RequireSeller Seller seller) {
        Order toShip = orderService.getOrderById(orderId);
        return ResponseEntity.ok(orderService.shipOrder(toShip));
    }

    @PutMapping(path = "/{orderId}/deliver", produces = { "application/json" })
    @ResponseBody
    public ResponseEntity deliverOrder(@PathVariable(name = "orderId") int orderId, @RequireBuyer Buyer buyer) {
        Order toDeliver = orderService.getOrderById(orderId);
        return ResponseEntity.ok(orderService.deliverOrder(toDeliver));
    }

    @PutMapping(path = "/{orderId}/cancel", produces = { "application/json" })
    @ResponseBody
    public ResponseEntity cancelOrder(@PathVariable(name = "orderId") int orderId, @RequireLoggedIn Account account) {
        Order toCancel = orderService.getOrderById(orderId);
        return ResponseEntity.ok(orderService.cancelOrder(toCancel));
    }

    private Product findProduct(Map<String, Object> request) {
        if (!request.containsKey("productId"))
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);

        Number productObjId = (Number) request.get("productId");
        int productId = productObjId.intValue();

        return productService.getProductById(productId);
    }
}
