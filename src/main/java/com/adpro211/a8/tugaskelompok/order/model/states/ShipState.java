package com.adpro211.a8.tugaskelompok.order.model.states;

import com.adpro211.a8.tugaskelompok.order.model.order.Order;

public class ShipState extends OrderState {

    final String DESC = "Ship";
    final DeliveredState deliveredState = new DeliveredState();

    @Override
    public String getStateDescription() {
        return DESC;
    }

    @Override
    public Order orderDelivered(Order order) {
        order.setStatus(deliveredState.getStateDescription());
        return order;
    }
}
