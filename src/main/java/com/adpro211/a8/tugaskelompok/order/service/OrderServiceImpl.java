package com.adpro211.a8.tugaskelompok.order.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.adpro211.a8.tugaskelompok.auths.models.account.Buyer;
import com.adpro211.a8.tugaskelompok.auths.models.account.Seller;
import com.adpro211.a8.tugaskelompok.auths.repository.AccountRepository;
import com.adpro211.a8.tugaskelompok.auths.service.AccountService;
import com.adpro211.a8.tugaskelompok.order.model.order.Order;
import com.adpro211.a8.tugaskelompok.order.model.item.Item;
import com.adpro211.a8.tugaskelompok.order.model.states.*;
import com.adpro211.a8.tugaskelompok.order.model.states.OpenState;
import com.adpro211.a8.tugaskelompok.order.repository.ItemRepository;
import com.adpro211.a8.tugaskelompok.order.repository.OrderRepository;
import com.adpro211.a8.tugaskelompok.product.model.Product;
import com.adpro211.a8.tugaskelompok.wallet.models.Wallet;
import com.adpro211.a8.tugaskelompok.wallet.repository.WalletRepository;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.util.*;

@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    OrderRepository orderRepository;

    @Autowired
    AccountService accountService;

    @Autowired
    ItemRepository itemRepository;

    @Autowired
    WalletRepository walletRepository;

    Map<String, OrderState> states = new HashMap<String, OrderState>();

    public OrderServiceImpl() {
        states.put("Open", new OpenState());
        states.put("Confirmed", new ConfirmedState());
        states.put("Ship", new ShipState());
        states.put("Delivered", new DeliveredState());
        states.put("Cancelled", new CancelledState());
    }

    public Order createOrder(Buyer buyer, int sellerId) {
        Order order = new Order();
        Seller seller;
        try {
            seller = (Seller) accountService.getAccountById(sellerId);
        } catch (NullPointerException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Seller doesn't exist");
        }

        order.setBuyer(buyer);
        order.setSeller(seller);
        order.setTotalPrice(0);
        order.setPaymentReceived(false);

        OrderState status = states.get("Open");
        order.setStatus(status.getStateDescription());

        List<Item> itemList = new ArrayList<Item>();
        order.setItems(itemList);

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

    public Order createItem(String name, int quantity, int orderId, Product product) {

        Item item = new Item();
        Order order = getOrderById(orderId);

        item.setName(name);
        item.setProduct(product);
        item.setOrder(order);
        item.setQuantity(quantity);
        item.setPrice(product.getPrice() * item.getQuantity());

        try {
            itemRepository.save(item);
        } catch (DataIntegrityViolationException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "This item already exist");
        }

        List<Item> itemsUpdate;
        if (order.getItems() == null)
            itemsUpdate = new ArrayList<Item>();
        else
            itemsUpdate = order.getItems();
        itemsUpdate.add(item);
        order.setItems(itemsUpdate);
        order.setTotalPrice(order.getTotalPrice() + item.getPrice());
        orderRepository.save(order);
        return order;
    }

    public boolean checkProductStock(int quantity, Product product) {
        Item item = new Item();

        item.setProduct(product);
        item.setQuantity(quantity);

        if (item.getQuantity() > item.getProduct().getStock())
            return false;

        return true;

    }

    public Item getItemById(int id) {
        return itemRepository.findItemById(id);
    }

    public Iterable<Item> getItemsByOrderId(int orderId) {
        Order order = orderRepository.findOrderById(orderId);
        return itemRepository.findAllByOrder(order);
    }

    public OrderState getStatus(Order order) {
        return states.get(order.getStatus());
    }

    public Order confirmOrder(Order order) {
        Order orderConfirmed = order;
        try {
            orderConfirmed = getStatus(order).confirmOrder(order);
        } catch (IllegalStateException e) {
            System.err.println(e.getMessage());
        }
        orderRepository.save(orderConfirmed);
        return orderConfirmed;
    }

    public Order cancelOrder(Order order) {
        Order orderCancelled = order;
        try {
            orderCancelled = getStatus(order).cancelOrder(order);
            orderCancelled.setFinished(true);
        } catch (IllegalStateException e) {
            System.err.println(e.getMessage());
        }
        orderRepository.save(orderCancelled);
        return orderCancelled;
    }

    public void updateWalletBalance(Buyer buyer, Seller seller, double price) {
        Wallet buyerWallet = buyer.getWallet();
        Wallet sellerWallet = seller.getWallet();
        buyerWallet.setBalance(buyerWallet.getBalance() - price);
        sellerWallet.setBalance(sellerWallet.getBalance() + price);
        buyer.setWallet(buyerWallet);
        seller.setWallet(sellerWallet);
        walletRepository.save(buyerWallet);
        walletRepository.save(sellerWallet);
    }

    public Order payOrder(Order order) {
        Buyer buyer = order.getBuyer();
        Seller seller = order.getSeller();
        double price = (double) order.getTotalPrice();
        if (order.getStatus().equals("Confirmed"))
            return order;

        try {
            updateWalletBalance(buyer, seller, price);
        } catch (NullPointerException e) {
            throw new NullPointerException("This buyer or this seller might not exist");
        }

        try {
            order.orderPayed();
        } catch (IllegalStateException e) {
            System.err.println(e.getMessage());
        }
        orderRepository.save(order);
        return order;
    }

    public Order shipOrder(Order order) {
        Order orderShipped = order;
        try {
            orderShipped = getStatus(order).shipOrder(order);
        } catch (IllegalStateException e) {
            System.err.println(e.getMessage());
        }
        orderRepository.save(orderShipped);
        return orderShipped;
    }

    public Order deliverOrder(Order order) {
        Order orderDelivered = order;
        try {
            orderDelivered = getStatus(order).orderDelivered(order);
            orderDelivered.setFinished(true);
        } catch (IllegalStateException e) {
            System.err.println(e.getMessage());
        }
        orderRepository.save(orderDelivered);
        return orderDelivered;
    }
}
