package com.adpro211.a8.tugaskelompok.order.model.states;

import com.adpro211.a8.tugaskelompok.order.model.order.Order;

public class CancelledState implements OrderState {
    Order order;
    int statusInt;

    public CancelledState(Order order) {
        this.order = order;
        this.statusInt = 4;
    }

    @Override
    public int getStatusInt() {
        return this.statusInt;
    }

    @Override
    public String getStateDescription() {
        return "Cancelled";
    }

    @Override
    public OrderState confirmOrder() {
        throw new IllegalStateException(
                "Can't confirm an order when the order is in " + getStateDescription() + " state.");
    }

    @Override
    public OrderState cancelOrder() {
        throw new IllegalStateException("The order is already cancelled.");
    }

    @Override
    public OrderState shipOrder() {
        throw new IllegalStateException(
                "Can't ship an order when the order is in " + getStateDescription() + " state.");
    }

    @Override
    public OrderState orderDelivered() {
        throw new IllegalStateException(
                "Can't deliver an order when the order is in " + getStateDescription() + " state.");
    }

    @Override
    public boolean isFinished() {
        return true;
    }
}
