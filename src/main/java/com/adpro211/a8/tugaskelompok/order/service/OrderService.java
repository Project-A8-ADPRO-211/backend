package com.adpro211.a8.tugaskelompok.order.service;

import com.adpro211.a8.tugaskelompok.auths.models.account.Buyer;
import com.adpro211.a8.tugaskelompok.auths.models.account.Seller;
import com.adpro211.a8.tugaskelompok.order.model.order.Order;
import com.adpro211.a8.tugaskelompok.order.model.item.Item;
import com.adpro211.a8.tugaskelompok.product.model.Product;

public interface OrderService {
    Order createOrder(Buyer buyer, int sellerId);

    Iterable<Order> getOrdersByBuyer(Buyer buyer);

    Iterable<Order> getOrdersBySeller(Seller seller);

    Order getOrderById(int id);

    Order createItem(String name, int quantity, int orderId, Product product);

    Item getItemById(int id);

    Iterable<Item> getItemsByOrderId(int orderId);

    boolean checkProductStock(int quantity, Product product);

    Order confirmOrder(Order order);

    Order cancelOrder(Order order);

    Order payOrder(Order order);

    Order shipOrder(Order order);

    Order deliverOrder(Order order);
}
