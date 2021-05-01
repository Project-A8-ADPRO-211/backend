package com.adpro211.a8.tugaskelompok.order.controller;

import com.adpro211.a8.tugaskelompok.order.model.Order;
import com.adpro211.a8.tugaskelompok.order.model.Item;
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

    @PostMapping(produces = {"application/json"}, path = )
    @ResponseBody
    public ResponseEntity postOrder(@RequestBody Order order){

    }
}
