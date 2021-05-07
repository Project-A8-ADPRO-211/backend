package com.adpro211.a8.tugaskelompok.order.model.states;

import com.adpro211.a8.tugaskelompok.order.model.order.Order;

public class DeliveredState implements OrderState {
    Order order;
    int statusInt;

    public DeliveredState(Order order) {
        this.order = order;
        this.statusInt = 3;
    }

    @Override
    public int getStatusInt() {
        return this.statusInt;
    }

    @Override
    public String getStateDescription() {
        return "Delivered";
    }

    @Override
    public OrderState confirmOrder() {
        throw new IllegalStateException("The order is already confirmed");
    }

    @Override
    public OrderState cancelOrder() {
        throw new IllegalStateException(
                "Can't cancel an order when the order is in " + getStateDescription() + " state.");
    }

    @Override
    public OrderState shipOrder() {
        throw new IllegalStateException(
                "Can't ship an order when the order is in " + getStateDescription() + " state.");
    }

    @Override
    public OrderState orderDelivered() {

        throw new IllegalStateException("The order is already delivered.");
    }

    @Override
    public boolean isFinished() {
        return true;
    }
}
