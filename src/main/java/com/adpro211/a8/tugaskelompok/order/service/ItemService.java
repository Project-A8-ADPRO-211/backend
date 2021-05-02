package com.adpro211.a8.tugaskelompok.order.service;

import com.adpro211.a8.tugaskelompok.order.model.item.Item;

public interface ItemService {
    Item createItem(String name, int quantity, int orderId, int accountId);

    Item getItemById(int id);

    Iterable<Item> getItemsByOrderId(int orderId);

}