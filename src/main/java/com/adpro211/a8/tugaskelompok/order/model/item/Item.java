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
    @Column(name = "items_id", updatable = false, nullable = false)
    private int items_id;

    @Column
    private String itemName;

    @Column
    private int itemQuantity;

    @OneToOne
    private Product associatedProduct;

    @OneToOne
    private Account productOwner;

    @ManyToOne
    private Order listedInOrder;

}
