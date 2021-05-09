package com.adpro211.a8.tugaskelompok.order.model.states;

import com.adpro211.a8.tugaskelompok.order.model.order.Order;

public class CancelledState implements OrderState {
    Order order;
    final String desc = "Cancelled";

    public CancelledState(Order order) {
        this.order = order;
        this.order.setStatusInt(4);
    }

    @Override
    public String getStateDescription() {
        return desc;
    }

    @Override
    public void confirmOrder() {
        throw new IllegalStateException("Can't confirm an order when the order is in " + desc + " state.");
    }

    @Override
    public void cancelOrder() {
        throw new IllegalStateException("The order is already cancelled.");
    }

    @Override
    public void shipOrder() {
        throw new IllegalStateException("Can't ship an order when the order is in " + desc + " state.");
    }

    @Override
    public void orderDelivered() {
        throw new IllegalStateException("Can't deliver an order when the order is in " + desc + " state.");
    }

    @Override
    public boolean isFinished() {
        return true;
    }
}
