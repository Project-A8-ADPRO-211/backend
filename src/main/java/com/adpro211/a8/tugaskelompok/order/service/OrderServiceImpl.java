package com.adpro211.a8.tugaskelompok.order.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.adpro211.a8.tugaskelompok.auths.models.account.Buyer;
import com.adpro211.a8.tugaskelompok.auths.models.account.Seller;
import com.adpro211.a8.tugaskelompok.auths.service.AccountService;
import com.adpro211.a8.tugaskelompok.order.model.order.Order;
import com.adpro211.a8.tugaskelompok.order.model.states.OpenState;
import com.adpro211.a8.tugaskelompok.order.repository.ItemRepository;
import com.adpro211.a8.tugaskelompok.order.repository.OrderRepository;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    OrderRepository orderRepository;

    @Autowired
    AccountService accountService;

    @Autowired
    ItemRepository itemRepository;

    public Order createOrder(boolean paymentReceived, int buyerId, int sellerId) {
        Order order = new Order();
        Buyer buyer = (Buyer) accountService.getAccountById(buyerId);
        Seller seller = (Seller) accountService.getAccountById(sellerId);

        order.setOrderBuyer(buyer);
        order.setOrderSeller(seller);
        order.setPaymentReceived(paymentReceived);
        order.setCurrentState(new OpenState(order));

        try {
            orderRepository.save(order);
        } catch (DataIntegrityViolationException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "This order already exist");
        }

        return order;
    }

    public Iterable<Order> getOrdersByBuyer(Buyer buyer) {
        return orderRepository.findAllByBuyer(buyer);
    }

    public Iterable<Order> getOrdersBySeller(Seller seller) {
        return orderRepository.findAllBySeller(seller);
    }

    public Order getOrderById(int id) {
        return orderRepository.findOrderById(id);
    }

    public Order confirmOrder(Order order) {
        try {
            order.confirmOrder();
        } catch (IllegalStateException e) {
            System.out.println(e.getMessage());
        }
        return order;
    }

    public Order cancelOrder(Order order) {
        try {
            order.cancelOrder();
        } catch (IllegalStateException e) {
            System.out.println(e.getMessage());
        }
        return order;
    }

    public Order payOrder(Order order) {
        try {
            order.orderPayed();
        } catch (IllegalStateException e) {
            System.out.println(e.getMessage());
        }
        return order;
    }

    public Order shipOrder(Order order) {
        try {
            order.shipOrder();
        } catch (IllegalStateException e) {
            System.out.println(e.getMessage());
        }
        return order;
    }

    public Order deliverOrder(Order order) {
        try {
            order.orderDelivered();
        } catch (IllegalStateException e) {
            System.out.println(e.getMessage());
        }
        return order;
    }
}
