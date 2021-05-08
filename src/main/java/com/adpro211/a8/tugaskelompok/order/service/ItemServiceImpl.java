package com.adpro211.a8.tugaskelompok.order.service;

import com.adpro211.a8.tugaskelompok.order.model.item.Item;
import com.adpro211.a8.tugaskelompok.order.model.order.Order;
import com.adpro211.a8.tugaskelompok.order.repository.ItemRepository;
import com.adpro211.a8.tugaskelompok.order.repository.OrderRepository;
import com.adpro211.a8.tugaskelompok.product.model.Product;
import com.adpro211.a8.tugaskelompok.product.service.ProductService;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import org.springframework.beans.factory.annotation.Autowired;

@Service
public class ItemServiceImpl implements ItemService {

    @Autowired
    ItemRepository itemRepository;

    @Autowired
    OrderRepository orderRepository;

    @Autowired
    ProductService productService;

    public Item createItem(String name, int quantity, int orderId, Product product) {

        Item item = new Item();
        Order order = orderRepository.findOrderById(orderId);

        item.setName(name);
        item.setQuantity(quantity);
        item.setProduct(product);
        item.setProductOwner(product.getOwnerAccount());
        item.setOrder(order);
        try {
            itemRepository.save(item);
        } catch (DataIntegrityViolationException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "This item already exist");
        }

        return item;
    }

    public Item getItemById(int id) {
        return itemRepository.findItemById(id);
    }

    public Iterable<Item> getItemsByOrderId(int orderId) {
        Order order = orderRepository.findOrderById(orderId);
        return itemRepository.findAllByOrder(order);
    }

}
