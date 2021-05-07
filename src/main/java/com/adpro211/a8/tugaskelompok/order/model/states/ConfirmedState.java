package com.adpro211.a8.tugaskelompok.order.model.states;

import com.adpro211.a8.tugaskelompok.order.model.order.Order;

public class ConfirmedState implements OrderState {

    Order order;
    int statusInt;

    public ConfirmedState(Order order) {
        this.order = order;
        this.statusInt = 1;
    }

    @Override
    public int getStatusInt() {
        return this.statusInt;
    }

    @Override
    public String getStateDescription() {
        return "Confirmed";
    }

    @Override
    public OrderState confirmOrder() {
        throw new IllegalStateException("The order is already confirmed");
    }

    @Override
    public OrderState cancelOrder() {
        return new CancelledState(order);
    }

    @Override
    public OrderState shipOrder() {
        if (!this.order.isPaymentReceived()) {
            throw new IllegalStateException("An order could not be shipped when payment is not received.");
        }
        return new ShipState(order);
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
}
