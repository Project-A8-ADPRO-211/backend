package com.adpro211.a8.tugaskelompok.order.model.states;

import com.adpro211.a8.tugaskelompok.order.model.order.Order;

public interface OrderState {

    public String getStateDescription();

    public Order confirmOrder(Order order);

    public Order cancelOrder(Order order);

    public Order shipOrder(Order order);

    public Order orderDelivered(Order order);

    public boolean isFinished();
}
