package com.adpro211.a8.tugaskelompok.order.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.adpro211.a8.tugaskelompok.auths.models.account.Buyer;
import com.adpro211.a8.tugaskelompok.auths.models.account.Seller;
import com.adpro211.a8.tugaskelompok.auths.repository.AccountRepository;
import com.adpro211.a8.tugaskelompok.auths.service.AccountService;
import com.adpro211.a8.tugaskelompok.order.model.order.Order;
import com.adpro211.a8.tugaskelompok.order.model.item.Item;
import com.adpro211.a8.tugaskelompok.order.model.states.OpenState;
import com.adpro211.a8.tugaskelompok.order.repository.ItemRepository;
import com.adpro211.a8.tugaskelompok.order.repository.OrderRepository;
import com.adpro211.a8.tugaskelompok.product.model.Product;
import com.adpro211.a8.tugaskelompok.product.service.ProductService;
import com.adpro211.a8.tugaskelompok.wallet.models.Wallet;
import com.adpro211.a8.tugaskelompok.wallet.repository.WalletRepository;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;
import java.util.ArrayList;
import java.util.List;

@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    OrderRepository orderRepository;

    @Autowired
    AccountRepository accountRepository;

    @Autowired
    ItemRepository itemRepository;

    @Autowired
    WalletRepository walletRepository;

    public Order createOrder(Buyer buyer, int sellerId) {
        Order order = new Order();
        Seller seller = (Seller) accountRepository.findById(sellerId).orElse(null);

        order.setBuyer(buyer);
        order.setSeller(seller);
        order.setTotalPrice(0);
        order.setPaymentReceived(false);
        order.setCurrentState(new OpenState(order));

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
        item.setProductOwner(product.getOwnerAccount());
        item.setOrder(order);
        item.setQuantity(quantity);
        item.setPrice(product.getPrice() * item.getQuantity());

        try {
            itemRepository.save(item);
        } catch (DataIntegrityViolationException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "This item already exist");
        }

        List<Item> itemsUpdate = order.getItems();
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

    public Order confirmOrder(Order order) {
        try {
            order.getCurrentState().confirmOrder();
        } catch (IllegalStateException e) {
            System.err.println(e.getMessage());
        }
        orderRepository.save(order);
        return order;
    }

    public Order cancelOrder(Order order) {
        try {
            order.getCurrentState().cancelOrder();
        } catch (IllegalStateException e) {
            System.err.println(e.getMessage());
        }
        orderRepository.save(order);
        return order;
    }

    public Order payOrder(Order order) {
        Buyer buyer = order.getBuyer();
        Seller seller = order.getSeller();
        double price = (double) order.getTotalPrice();

        try {
            Wallet buyerWallet = buyer.getWallet();
            Wallet sellerWallet = seller.getWallet();
            buyerWallet.setBalance(buyerWallet.getBalance() - price);
            sellerWallet.setBalance(sellerWallet.getBalance() + price);
            buyer.setWallet(buyerWallet);
            seller.setWallet(sellerWallet);
            walletRepository.save(buyerWallet);
            walletRepository.save(sellerWallet);
        } catch (NullPointerException e) {
            throw new NullPointerException("This buyer or this seller might not exist");
        }

        accountRepository.save(buyer);
        accountRepository.save(seller);
        order.setBuyer(buyer);
        order.setSeller(seller);

        try {
            order.orderPayed();
        } catch (IllegalStateException e) {
            System.err.println(e.getMessage());
        }
        orderRepository.save(order);
        return order;
    }

    public Order shipOrder(Order order) {
        try {
            order.getCurrentState().shipOrder();
        } catch (IllegalStateException e) {
            System.err.println(e.getMessage());
        }
        orderRepository.save(order);
        return order;
    }

    public Order deliverOrder(Order order) {
        try {
            order.getCurrentState().orderDelivered();
        } catch (IllegalStateException e) {
            System.err.println(e.getMessage());
        }
        orderRepository.save(order);
        return order;
    }
}
