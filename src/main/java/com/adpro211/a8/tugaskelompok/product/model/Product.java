package com.adpro211.a8.tugaskelompok.product.model;

import com.adpro211.a8.tugaskelompok.auths.models.account.Account;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "product")
@Data
@NoArgsConstructor
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "product_id")
    private int productId;

    @Column(name = "product_name")
    private String productName;

    @Column(name = "description")
    private String description;

    @Column(name = "price" ,nullable = false)
    private int price;

    @Column(name = "stock" ,nullable = false)
    private int stock;

    @ManyToOne
    private Account ownerAccount;
}
