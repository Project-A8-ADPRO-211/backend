package com.adpro211.a8.tugaskelompok.order.model.order;

import com.adpro211.a8.tugaskelompok.auths.models.account.Buyer;
import com.adpro211.a8.tugaskelompok.auths.models.account.Seller;
import com.adpro211.a8.tugaskelompok.order.model.item.Item;
import com.adpro211.a8.tugaskelompok.order.model.states.OpenState;
import com.fasterxml.jackson.annotation.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
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

    @Column(name = "totalPrice")
    private int totalPrice;

    @Column(name = "payment_received")
    private boolean paymentReceived;

    @Column
    private String status;

    @Column
    private boolean isFinished;

    @JsonManagedReference
    @OneToMany(mappedBy = "order")
    private List<Item> items;

    @Column(name = "order_time", columnDefinition = "TIMESTAMP")
    private LocalDateTime orderTime;

    @Column(name = "payment_time", columnDefinition = "TIMESTAMP")
    private LocalDateTime paymentTime;

    @Column(name = "ship_time", columnDefinition = "TIMESTAMP")
    private LocalDateTime shipTime;

    @Column(name = "completed_time", columnDefinition = "TIMESTAMP")
    private LocalDateTime completedTime;

    @ManyToOne
    private Buyer buyer;

    @ManyToOne
    private Seller seller;

    public Order() {
        setStatus(new OpenState().getStateDescription());
        setOrderTime(LocalDateTime.now());
    }

    public void orderPayed() {
        this.setPaymentReceived(true);
    }

}
