package com.adpro211.a8.tugaskelompok.order.service;

import com.adpro211.a8.tugaskelompok.order.model.item.Item;
import com.adpro211.a8.tugaskelompok.product.model.Product;

public interface ItemService {
    Item createItem(String name, int quantity, int orderId, Product product);

    Item getItemById(int id);

    Iterable<Item> getItemsByOrderId(int orderId);

}
