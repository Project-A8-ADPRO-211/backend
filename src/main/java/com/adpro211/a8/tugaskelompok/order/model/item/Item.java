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
    @Column(name = "id")
    private int id;

    @Column(name = "item_name")
    private String name;

    @Column(name = "quantity")
    private int quantity;

    @Column(name = "item_price")
    private int price;

    @OneToOne
    private Product product;

    @ManyToOne
    private Order order;

}
