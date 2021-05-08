package com.adpro211.a8.tugaskelompok.order.model.states;

import com.adpro211.a8.tugaskelompok.order.model.order.Order;

public class OpenState implements OrderState {

    Order order;

    public OpenState(Order order) {
        this.order = order;
        this.order.setStatusInt(0);
    }

    @Override
    public String getStateDescription() {
        return "Open";
    }

    @Override
    public void confirmOrder() {
        this.order.setCurrentState(new ConfirmedState(this.order));
    }

    @Override
    public void cancelOrder() {
        this.order.setCurrentState(new CancelledState(this.order));
    }

    @Override
    public void shipOrder() {
        throw new IllegalStateException(
                "Can't ship an order when the order is in " + getStateDescription() + " state.");
    }

    @Override
    public void orderDelivered() {
        throw new IllegalStateException(
                "Can't deliver an order when the order is in " + getStateDescription() + " state.");
    }

    @Override
    public boolean isFinished() {
        return false;
    }

}
