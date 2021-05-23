package com.adpro211.a8.tugaskelompok.order.model.states;

import com.adpro211.a8.tugaskelompok.order.model.order.Order;

public class ConfirmedState implements OrderState {

    final static String desc = "Confirmed";
    final CancelledState cancelledState = new CancelledState();
    final ShipState shipState = new ShipState();

    @Override
    public String getStateDescription() {
        return desc;
    }

    @Override
    public Order confirmOrder(Order order) {
        throw new IllegalStateException("Can't confirm an order when the order is in " + desc + " state");
    }

    @Override
    public Order cancelOrder(Order order) {
        throw new IllegalStateException("Can't cancel an order when the order is in " + desc + " state");
    }

    @Override
    public Order shipOrder(Order order) {
        if (!order.isPaymentReceived()) {
            throw new IllegalStateException("An order could not be shipped when payment is not received");
        }
        order.setStatus(shipState.getStateDescription());
        return order;
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
