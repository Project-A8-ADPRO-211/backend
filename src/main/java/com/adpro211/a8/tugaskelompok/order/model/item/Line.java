package com.adpro211.a8.tugaskelompok.order.model.item;

import com.adpro211.a8.tugaskelompok.auths.models.account.Account;
import com.adpro211.a8.tugaskelompok.order.model.order.Order;
import com.adpro211.a8.tugaskelompok.order.model.states.OrderState;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "line")
@Data
@NoArgsConstructor
public class Line {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "line_id", updatable = false, nullable = false)
    private int line_id;

    @ManyToOne
    private Order listedInOrder;

    @OneToOne
    private Account productsOwner;

    @OneToMany(mappedBy = "listedInLine")
    private List<Item> itemsConsisted;

    @Column
    private boolean paymentReceived;

    private OrderState lineState = listedInOrder.getCurrentState();

    public String getStateDescription() {
        return lineState.getStateDescription();
    }

    public void confirmOrder() {
        lineState = lineState.confirmOrder();
    }

    public void cancelOrder() {
        lineState = lineState.cancelOrder();
    }

    public void orderPayed() {
        setPaymentReceived(true);
    }

    public void shipOrder() {
        lineState = lineState.shipOrder();
    }

    public void orderDelivered() {
        lineState = lineState.orderDelivered();
    }

    public boolean isFinished() {
        return lineState.isFinished();
    }
}
