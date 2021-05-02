package com.adpro211.a8.tugaskelompok.order.model.states;

public interface OrderState {

    public String getStateDescription();

    public OrderState confirmOrder();

    public OrderState cancelOrder();

    public OrderState shipOrder();

    public OrderState orderDelivered();

    public boolean isFinished();
}
