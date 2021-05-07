package com.adpro211.a8.tugaskelompok.order.model.order;

import com.adpro211.a8.tugaskelompok.auths.models.account.Buyer;
import com.adpro211.a8.tugaskelompok.auths.models.account.Seller;
import com.adpro211.a8.tugaskelompok.order.model.item.Item;
import com.adpro211.a8.tugaskelompok.order.model.states.CancelledState;
import com.adpro211.a8.tugaskelompok.order.model.states.ConfirmedState;
import com.adpro211.a8.tugaskelompok.order.model.states.DeliveredState;
import com.adpro211.a8.tugaskelompok.order.model.states.OpenState;
import com.adpro211.a8.tugaskelompok.order.model.states.OrderState;
import com.adpro211.a8.tugaskelompok.order.model.states.ShipState;
import com.fasterxml.jackson.annotation.JsonIgnore;
import java.util.List;
import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "order")
@Data
public class Order {

    @Transient
    OrderState openState;
    @Transient
    OrderState confirmedState;
    @Transient
    OrderState shipState;
    @Transient
    OrderState deliveredState;
    @Transient
    OrderState cancelledState;

    @Transient
    private OrderState currentState;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "order_id", updatable = false, nullable = false)
    private int Id;

    @OneToMany(mappedBy = "listedInOrder")
    @JsonIgnore
    private List<Item> itemList;

    @Column
    private boolean paymentReceived;

    @ManyToOne
    private Buyer orderBuyer;

    @ManyToOne
    private Seller orderSeller;

    @Column
    private int statusInt;

    public Order() {
        openState = new OpenState(this);
        confirmedState = new ConfirmedState(this);
        shipState = new ShipState(this);
        deliveredState = new DeliveredState(this);
        cancelledState = new CancelledState(this);
    }

    public String getStateDescription() {
        return currentState.getStateDescription();
    }

    public void confirmOrder() {
        currentState = currentState.confirmOrder();
    }

    public void cancelOrder() {
        currentState = currentState.cancelOrder();
    }

    public void orderPayed() {
        setPaymentReceived(true);
    }

    public void shipOrder() {
        currentState = currentState.shipOrder();
    }

    public void orderDelivered() {
        currentState = currentState.orderDelivered();
    }

    public boolean isFinished() {
        return currentState.isFinished();
    }

}
