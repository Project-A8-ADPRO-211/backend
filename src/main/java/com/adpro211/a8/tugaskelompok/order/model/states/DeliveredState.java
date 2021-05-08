package com.adpro211.a8.tugaskelompok.order.model.states;

import com.adpro211.a8.tugaskelompok.order.model.order.Order;

public class DeliveredState implements OrderState {
    Order order;

    public DeliveredState(Order order) {
        this.order = order;
        this.order.setStatusInt(3);
    }

    @Override
    public String getStateDescription() {
        return "Delivered";
    }

    @Override
    public void confirmOrder() {
        throw new IllegalStateException("The order is already confirmed");
    }

    @Override
    public void cancelOrder() {
        throw new IllegalStateException(
                "Can't cancel an order when the order is in " + getStateDescription() + " state.");
    }

    @Override
    public void shipOrder() {
        throw new IllegalStateException(
                "Can't ship an order when the order is in " + getStateDescription() + " state.");
    }

    @Override
    public void orderDelivered() {

        throw new IllegalStateException("The order is already delivered.");
    }

    @Override
    public boolean isFinished() {
        return true;
    }
}
