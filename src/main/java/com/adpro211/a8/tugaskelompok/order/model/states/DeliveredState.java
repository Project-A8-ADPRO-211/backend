package com.adpro211.a8.tugaskelompok.order.model.states;

public class DeliveredState extends OrderState {

    final String DESC = "Delivered";

    @Override
    public String getStateDescription() {
        return DESC;
    }

    @Override
    public boolean isFinished() {
        return true;
    }
}
