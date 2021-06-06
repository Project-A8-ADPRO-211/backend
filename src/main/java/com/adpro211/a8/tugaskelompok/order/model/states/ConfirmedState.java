package com.adpro211.a8.tugaskelompok.order.model.states;

import com.adpro211.a8.tugaskelompok.order.model.order.Order;

public class ConfirmedState extends OrderState {

    final static String DESC = "Confirmed";
    final CancelledState cancelledState = new CancelledState();
    final ShipState shipState = new ShipState();

    @Override
    public String getStateDescription() {
        return DESC;
    }

    @Override
    public Order shipOrder(Order order) {
        if (!order.isPaymentReceived()) {
            throw new IllegalStateException("An order could not be shipped when payment is not received");
        }
        order.setStatus(shipState.getStateDescription());
        return order;
    }
}
