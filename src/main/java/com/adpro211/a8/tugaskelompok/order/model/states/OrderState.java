package com.adpro211.a8.tugaskelompok.order.model.states;

import java.util.List;

import com.adpro211.a8.tugaskelompok.order.model.item.Item;

public interface OrderState {

    public String getStateDescription();

    // public OrderState addItem(List<Item> items);

    public OrderState confirmOrder();

    public OrderState cancelOrder();

    public OrderState shipOrder();

    public OrderState orderDelivered();

    public boolean isFinished();

    // public OrderState orderLost();

    // public OrderState orderFound();
}
