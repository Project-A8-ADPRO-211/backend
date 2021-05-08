package com.adpro211.a8.tugaskelompok.order.model.item;

import com.adpro211.a8.tugaskelompok.auths.models.account.Account;
import com.adpro211.a8.tugaskelompok.order.model.order.Order;
import com.adpro211.a8.tugaskelompok.product.model.Product;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.persistence.*;

@Entity
@Table(name = "item")
@Data
@NoArgsConstructor
public class Item {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "item_id")
    private int id;

    @Column(name = "item_name")
    private String name;

    @Column(name = "quantity")
    private int quantity;

    @OneToOne
    private Product product;

    @OneToOne
    private Account productOwner;

    @ManyToOne
    private Order order;

}
