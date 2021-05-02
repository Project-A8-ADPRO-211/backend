package com.adpro211.a8.tugaskelompok.order.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.adpro211.a8.tugaskelompok.auths.models.account.Account;
import com.adpro211.a8.tugaskelompok.auths.repository.AccountRepository;
import com.adpro211.a8.tugaskelompok.order.model.order.Order;
import com.adpro211.a8.tugaskelompok.order.repository.OrderRepository;
import com.adpro211.a8.tugaskelompok.order.model.item.Item;

@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    OrderRepository orderRepository;

    @Autowired
    AccountRepository accountRepository;

    public Order createOrder(boolean paymentReceived, String email) {
        return null;
    }

    public Iterable<Order> getOrdersbyAccountEmail(String name) {
        return null;
    }

    public Order getOrderById(int id) {
        return null;
    }

    public Order confirmOrder(Order order) {
        return null;
    }

    public Order cancelOrder(Order order) {
        return null;
    }

    public Order payOrder(Order order) {
        return null;
    }

    public Order shipOrder(Order order) {
        return null;
    }

    public Order deliverOrder(Order order) {
        return null;
    }
}
