package com.adpro211.a8.tugaskelompok.order.service;

import com.adpro211.a8.tugaskelompok.order.model.order.Order;

public interface OrderService {
    Order createOrder(boolean paymentReceived, String email);

    Iterable<Order> getOrdersbyAccountEmail(String name);

    Order getOrderById(int id);

    Order confirmOrder(Order order);

    Order cancelOrder(Order order);

    Order payOrder(Order order);

    Order shipOrder(Order order);

    Order deliverOrder(Order order);
}
