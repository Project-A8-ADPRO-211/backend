package com.adpro211.a8.tugaskelompok.auths.models.account;

import com.adpro211.a8.tugaskelompok.auths.models.authStrategy.AuthStrategy;
import com.adpro211.a8.tugaskelompok.product.model.Product;
import com.adpro211.a8.tugaskelompok.product.model.Review;
import com.adpro211.a8.tugaskelompok.wallet.models.Wallet;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "account_tbl")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "account_type", discriminatorType = DiscriminatorType.STRING)
@Setter
@Getter
@NoArgsConstructor
public abstract class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @Column
    private String name;

    @Column(unique = true)
    private String email;

    @OneToMany(mappedBy = "assignedUser")
    @JsonIgnore
    private Set<AuthStrategy> authStrategies;

    @OneToMany(mappedBy = "ownerAccount")
    @JsonIgnore
    private List<Product> productList;

    @OneToMany(mappedBy = "reviewer")
    @JsonIgnore
    private List<Review> reviewList;

    @Column(name = "account_type", nullable = false, insertable = false, updatable = false)
    private String accountType;

    @OneToOne(mappedBy = "account")
    @JsonIgnoreProperties({"account"})
    private Wallet wallet;

}
