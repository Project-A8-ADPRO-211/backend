package com.adpro211.a8.tugaskelompok.order.model.states;

import java.util.List;

import com.adpro211.a8.tugaskelompok.order.model.order.Order;

public class OpenState implements OrderState {

    Order order;

    public OpenState(Order order) {
        this.order = order;
    }

    @Override
    public String getStateDescription() {
        return "Open";
    }

    // public OrderState addItem(List<Item> items);

    @Override
    public OrderState confirmOrder() {
        return new ConfirmedState(order);
    }

    @Override
    public OrderState cancelOrder() {
        return new CancelledState(order);
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
        return false;
    }

    // public OrderState orderLost();

    // public OrderState orderFound();

}
