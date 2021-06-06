package com.adpro211.a8.tugaskelompok.order.model.states;

import com.adpro211.a8.tugaskelompok.order.model.order.Order;

public abstract class OrderState {

    public abstract String getStateDescription();

    public Order confirmOrder(Order order) {
        throw new IllegalStateException("Can't confirm an order when the order is " + getStateDescription());
    }

    public Order cancelOrder(Order order) {
        throw new IllegalStateException("Can't cancel an order when the order is " + getStateDescription());
    }

    public Order shipOrder(Order order) {
        throw new IllegalStateException("Can't ship an order when the order is " + getStateDescription());
    }

    public Order orderDelivered(Order order) {
        throw new IllegalStateException("Can't deliver an order when the order is " + getStateDescription());
    }

    public boolean isFinished() {
        return false;
    }
}
