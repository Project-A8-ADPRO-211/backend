package com.adpro211.a8.tugaskelompok.order.model.states;

public interface OrderState {

    public String getStateDescription();

    public void confirmOrder();

    public void cancelOrder();

    public void shipOrder();

    public void orderDelivered();

    public boolean isFinished();
}
