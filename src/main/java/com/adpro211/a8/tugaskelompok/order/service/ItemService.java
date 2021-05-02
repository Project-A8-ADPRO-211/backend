package com.adpro211.a8.tugaskelompok.order.service;

import com.adpro211.a8.tugaskelompok.order.model.item.Item;
import com.adpro211.a8.tugaskelompok.order.model.order.Order;

public interface ItemService {
    Item createItem(String name, int quantity, int orderId, int accountId);

    Item getItemById(int id);

    Iterable<Item> getItemsByOrder(Order order);

    Iterable<Item> getAllItems();

}
