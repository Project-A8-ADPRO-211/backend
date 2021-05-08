package com.adpro211.a8.tugaskelompok.product.model;

import com.adpro211.a8.tugaskelompok.auths.models.account.Account;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "product")
@Setter
@Getter
@NoArgsConstructor
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private int id;

    @Column(name = "name", nullable = false, unique = true)
    private String name;

    @Column(name = "description", nullable = false)
    private String description;

    @Column(name = "image_url", nullable = false)
    private String imageUrl;

    @Column(name = "price" , nullable = false)
    private int price;

    @Column(name = "stock" , nullable = false)
    private int stock;

    @OneToMany(mappedBy = "productReview", cascade=CascadeType.REMOVE)
    @JsonIgnore
    private List<Review> reviewList;

    @ManyToOne
    private Account ownerAccount;
}
