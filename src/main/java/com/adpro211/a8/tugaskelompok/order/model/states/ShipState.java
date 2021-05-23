package com.adpro211.a8.tugaskelompok.order.model.states;

import com.adpro211.a8.tugaskelompok.order.model.order.Order;

public class ShipState implements OrderState {

    final String desc = "Ship";
    final DeliveredState deliveredState = new DeliveredState();

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
        throw new IllegalStateException("Can't ship an order when the order is in " + desc + " state");
    }

    @Override
    public Order orderDelivered(Order order) {
        order.setStatus(deliveredState.getStateDescription());
    }

    @Override
    public boolean isFinished() {
        return false;
    }
}
