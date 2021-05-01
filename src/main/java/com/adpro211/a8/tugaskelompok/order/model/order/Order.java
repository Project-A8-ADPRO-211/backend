package com.adpro211.a8.tugaskelompok.order.model.order;

import com.adpro211.a8.tugaskelompok.auths.models.account.Account;
import com.adpro211.a8.tugaskelompok.order.model.item.Line;
import com.adpro211.a8.tugaskelompok.order.model.states.OpenState;
import com.adpro211.a8.tugaskelompok.order.model.states.OrderState;
import com.fasterxml.jackson.annotation.JsonIgnore;

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

    @OneToMany(mappedBy = "listedInOrder")
    @JsonIgnore
    private List<Line> linesList;

    @Column
    private boolean paymentReceived;

    @ManyToOne
    private Account orderAccount;

    public String getStateDescription() {
        return currentState.getStateDescription();
    }

    public void confirmOrder() {
        for (Line line : getLinesList()) {
            line.confirmOrder();
        }
        currentState = currentState.confirmOrder();
    }

    public void cancelOrder() {
        for (Line line : getLinesList()) {
            line.cancelOrder();
        }
        currentState = currentState.cancelOrder();
    }

    public void orderPayed() {
        for (Line line : getLinesList()) {
            line.setPaymentReceived(true);
        }
        setPaymentReceived(true);
    }

    public void shipOrder() {

        // masing2 line punya state masing2
        if (allLineIsShipped()) {
            currentState = currentState.shipOrder();
        }
    }

    public void orderDelivered() {

        // masing2 line punya state masing2
        if (allLineIsDelivered()) {
            currentState = currentState.orderDelivered();
        }
    }

    public boolean isFinished() {
        return currentState.isFinished();
    }

    private boolean allLineIsShipped() {
        for (Line line : getLinesList()) {
            if (!line.getStateDescription().equals("Shipped"))
                return false;
        }
        return true;
    }

    private boolean allLineIsDelivered() {
        for (Line line : getLinesList()) {
            if (!line.getStateDescription().equals("Delivered"))
                return false;
        }
        return true;
    }

}
