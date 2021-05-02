package com.adpro211.a8.tugaskelompok.order.service;

import com.adpro211.a8.tugaskelompok.auths.models.account.Buyer;
import com.adpro211.a8.tugaskelompok.auths.models.account.Seller;
import com.adpro211.a8.tugaskelompok.order.model.order.Order;

public interface OrderService {
    Order createOrder(boolean paymentReceived, int buyerId, int sellerId);

    Iterable<Order> getOrdersByBuyer(Buyer buyer);

    Iterable<Order> getOrdersBySeller(Seller seller);

    Order getOrderById(int id);

    Order confirmOrder(Order order);

    Order cancelOrder(Order order);

    Order payOrder(Order order);

    Order shipOrder(Order order);

    Order deliverOrder(Order order);
}
