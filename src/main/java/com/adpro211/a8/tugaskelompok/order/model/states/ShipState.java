package com.adpro211.a8.tugaskelompok.order.model.states;

import com.adpro211.a8.tugaskelompok.order.model.order.Order;

public class ShipState implements OrderState {
    Order order;

    public ShipState(Order order) {
        this.order = order;
        this.order.setStatusInt(2);
    }

    @Override
    public String getStateDescription() {
        return "Ship";
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
        throw new IllegalStateException("The order is already shipped");
    }

    @Override
    public void orderDelivered() {
        this.order.setCurrentState(new DeliveredState(this.order));
    }

    @Override
    public boolean isFinished() {
        return false;
    }
}
