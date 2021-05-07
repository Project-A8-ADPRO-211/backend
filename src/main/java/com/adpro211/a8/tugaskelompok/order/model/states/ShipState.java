package com.adpro211.a8.tugaskelompok.order.model.states;

import com.adpro211.a8.tugaskelompok.order.model.order.Order;

public class ShipState implements OrderState {
    Order order;
    int statusInt;

    public ShipState(Order order) {
        this.order = order;
        this.statusInt = 2;
    }

    @Override
    public int getStatusInt() {
        return this.statusInt;
    }

    @Override
    public String getStateDescription() {
        return "Ship";
    }

    // public OrderState addItem(List<Item> items);

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
        throw new IllegalStateException("The order is already shipped");
    }

    @Override
    public OrderState orderDelivered() {
        return new DeliveredState(order);
    }

    @Override
    public boolean isFinished() {
        return false;
    }
}
