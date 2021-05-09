package com.adpro211.a8.tugaskelompok.order.model.states;

import com.adpro211.a8.tugaskelompok.order.model.order.Order;

public class ConfirmedState implements OrderState {

    Order order;
    final String desc = "Confirmed";

    public ConfirmedState(Order order) {
        this.order = order;
        this.order.setStatusInt(1);
    }

    @Override
    public String getStateDescription() {
        return this.desc;
    }

    @Override
    public void confirmOrder() {
        throw new IllegalStateException("Can't confirm an order when the order is in " + desc + " state");
    }

    @Override
    public void cancelOrder() {
        this.order.setCurrentState(new CancelledState(this.order));
    }

    @Override
    public void shipOrder() {
        if (!this.order.isPaymentReceived()) {
            throw new IllegalStateException("An order could not be shipped when payment is not received");
        }
        this.order.setCurrentState(new ShipState(this.order));
    }

    @Override
    public void orderDelivered() {
        throw new IllegalStateException("Can't deliver an order when the order is in " + desc + " state");
    }

    @Override
    public boolean isFinished() {
        return false;
    }
}
