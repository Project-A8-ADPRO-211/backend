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
@Table(name = "user_order")
@Data
public class Order {

    @Transient
    private OrderState currentState;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private int id;

    @OneToMany(mappedBy = "order")
    @JsonIgnore
    private List<Item> items;

    @Column(name = "payment_received")
    private boolean paymentReceived;

    @ManyToOne
    private Buyer buyer;

    @ManyToOne
    private Seller seller;

    @Column(name = "status_int")
    private int statusInt;

    public Order() {
        setCurrentState(new OpenState(this));
    }

    public String getStateDescription() {
        return currentState.getStateDescription();
    }

    public void confirmOrder() {
        currentState.confirmOrder();
    }

    public void cancelOrder() {
        currentState.cancelOrder();
    }

    public void orderPayed() {
        setPaymentReceived(true);
    }

    public void shipOrder() {
        currentState.shipOrder();
    }

    public void orderDelivered() {
        currentState.orderDelivered();
    }

    public boolean isFinished() {
        return currentState.isFinished();
    }

}
