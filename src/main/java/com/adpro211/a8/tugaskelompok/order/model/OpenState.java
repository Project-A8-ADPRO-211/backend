package com.adpro211.a8.tugaskelompok.order.model;

import java.util.List;

public class OpenState implements OrderState {

    Order order;

    public OpenState(Order order) {
        this.order = order;
    }

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
