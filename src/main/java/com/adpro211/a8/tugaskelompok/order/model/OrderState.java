package com.adpro211.a8.tugaskelompok.order.model;

import com.adpro211.a8.tugaskelompok.order.model.Item;

import java.util.List;

public interface OrderState {

    protected String getStateDescription();

    public OrderState addItem(List<Item> items);

    public OrderState confirmOrder();

    public OrderState cancelOrder();

    public OrderState shipOrder();

    public OrderState orderLost();

    public OrderState orderFound();

    public OrderState orderDelivered();

    public boolean isFinished();
}
