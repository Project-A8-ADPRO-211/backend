package com.adpro211.a8.tugaskelompok.order.model.states;

import com.adpro211.a8.tugaskelompok.order.model.order.Order;

public class OpenState implements OrderState {

    final static String desc = "Open";
    final ConfirmedState confirmedState = new ConfirmedState();
    final CancelledState cancelledState = new CancelledState();

    @Override
    public String getStateDescription() {
        return desc;
    }

    @Override
    public Order confirmOrder(Order order) {
        order.setStatus(confirmedState.getStateDescription());
        order.setPaymentReceived(true);
        return order;
    }

    @Override
    public Order cancelOrder(Order order) {
        order.setStatus(cancelledState.getStateDescription());
        return order;
    }

    @Override
    public Order shipOrder(Order order) {
        throw new IllegalStateException("Can't ship an order when the order is in " + desc + " state");

    }

    @Override
    public Order orderDelivered(Order order) {
        throw new IllegalStateException("Can't deliver an order when the order is in " + desc + " state");
    }

    @Override
    public boolean isFinished() {
        return false;
    }

}
