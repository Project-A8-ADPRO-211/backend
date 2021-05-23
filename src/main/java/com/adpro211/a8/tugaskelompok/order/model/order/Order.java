package com.adpro211.a8.tugaskelompok.order.model.order;

import com.adpro211.a8.tugaskelompok.auths.models.account.Buyer;
import com.adpro211.a8.tugaskelompok.auths.models.account.Seller;
import com.adpro211.a8.tugaskelompok.order.model.item.Item;
import com.adpro211.a8.tugaskelompok.order.model.states.OpenState;
import com.adpro211.a8.tugaskelompok.order.model.states.OrderState;
import com.fasterxml.jackson.annotation.JsonIgnore;
import java.util.List;
import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "order_queue")
@Data
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private int id;

    @OneToMany(mappedBy = "order")
    @JsonIgnore
    private List<Item> items;

    @Column(name = "totalPrice")
    private int totalPrice;

    @Column(name = "payment_received")
    private boolean paymentReceived;

    @Column
    private String status;

    @Column
    private boolean isFinished;

    @ManyToOne
    private Buyer buyer;

    @ManyToOne
    private Seller seller;

    public Order() {
        setStatus(new OpenState().getStateDescription());
    }

    public void orderPayed() {
        this.setPaymentReceived(true);
    }

}
