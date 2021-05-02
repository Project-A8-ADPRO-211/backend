package com.adpro211.a8.tugaskelompok.order.service;

import com.adpro211.a8.tugaskelompok.order.model.item.Item;
import com.adpro211.a8.tugaskelompok.order.model.order.Order;
import com.adpro211.a8.tugaskelompok.order.repository.ItemRepository;
import com.adpro211.a8.tugaskelompok.order.repository.OrderRepository;
import com.adpro211.a8.tugaskelompok.product.model.Product;
import com.adpro211.a8.tugaskelompok.product.service.ProductService;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import org.springframework.beans.factory.annotation.Autowired;

public class ItemServiceImpl implements ItemService {

    @Autowired
    ItemRepository itemRepository;

    @Autowired
    OrderRepository orderRepository;

    @Autowired
    ProductService productService;

    public Item createItem(String name, int quantity, int orderId, int productId) {

        Item item = new Item();
        Product product = productService.getProductById(productId);
        Order order = orderRepository.findOrderById(orderId);

        item.setItemName(name);
        item.setItemQuantity(quantity);
        item.setAssociatedProduct(product);
        item.setProductOwner(product.getOwnerAccount());
        item.setListedInOrder(order);
        try {
            itemRepository.save(item);
        } catch (DataIntegrityViolationException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "This item already exist");
        }

        return item;
    }

    public Item getItemById(int id) {
        return itemRepository.findItembyId(id);
    }

    public Iterable<Item> getItemsByOrderId(int orderId) {
        Order order = orderRepository.findOrderById(orderId);
        return itemRepository.findAllByOrder(order);
    }

}
