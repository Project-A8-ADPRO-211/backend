package com.adpro211.a8.tugaskelompok.order.model.order;

import com.adpro211.a8.tugaskelompok.auths.models.account.Account;
import com.adpro211.a8.tugaskelompok.order.model.item.Item;
import com.adpro211.a8.tugaskelompok.order.model.states.OrderState;
import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "order")
@Data
@NoArgsConstructor
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "order_id", updatable = false, nullable = false)
    private int order_id;

    @OneToMany(mappedBy = "listedInOrder")
    @JsonIgnore
    private Iterable<Item> itemList;

    @Column
    private boolean paymentReceived;

    @ManyToOne
    private Account orderBuyer;

    @ManyToOne
    private Account orderSeller;

    @Column
    private OrderState currentState;

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
