package com.adpro211.a8.tugaskelompok.order.model.states;

public class CancelledState extends OrderState {

    final static String DESC = "Cancelled";

    @Override
    public String getStateDescription() {
        return DESC;
    }

    @Override
    public boolean isFinished() {
        return true;
    }
}
