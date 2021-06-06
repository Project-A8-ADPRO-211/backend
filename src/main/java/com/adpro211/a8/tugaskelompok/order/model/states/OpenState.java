package com.adpro211.a8.tugaskelompok.order.model.states;

import com.adpro211.a8.tugaskelompok.order.model.order.Order;

public class OpenState extends OrderState {

    final static String DESC = "Open";
    final ConfirmedState confirmedState = new ConfirmedState();
    final CancelledState cancelledState = new CancelledState();

    @Override
    public String getStateDescription() {
        return DESC;
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

}
