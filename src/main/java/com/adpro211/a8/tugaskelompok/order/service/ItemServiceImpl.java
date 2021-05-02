package com.adpro211.a8.tugaskelompok.order.service;

import com.adpro211.a8.tugaskelompok.order.model.item.Item;
import com.adpro211.a8.tugaskelompok.order.model.order.Order;
import com.adpro211.a8.tugaskelompok.order.repository.ItemRepository;
import com.adpro211.a8.tugaskelompok.order.repository.OrderRepository;

import org.springframework.beans.factory.annotation.Autowired;

public class ItemServiceImpl implements ItemService {

    @Autowired
    ItemRepository itemRepository;

    @Autowired
    OrderRepository orderRepository;

    public Item createItem(String name, int quantity, int orderId) {
        return null;
    }

    public Item getItemById(int id) {
        return null;
    }

    public Iterable<Item> getItemsByOrderId(int id) {
        return null;
    }
}
