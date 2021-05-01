package com.adpro211.a8.tugaskelompok.order.model;

import com.adpro211.a8.tugaskelompok.auths.models.account.Account;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "order")
@Data
@NoArgsConstructor
public class Order {

    private OrderState currentState = new OpenState(this);

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "order_id", updatable = false, nullable = false)
    private int order_id;

    @OneToMany(mappedBy = "orderOwner")
    private List<Item> itemList;

    @Column
    private boolean paymentReceived;

    @OneToOne
    private Account orderAccount;

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
