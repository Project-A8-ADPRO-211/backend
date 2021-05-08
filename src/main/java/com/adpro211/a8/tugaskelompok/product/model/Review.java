package com.adpro211.a8.tugaskelompok.product.model;

import com.adpro211.a8.tugaskelompok.auths.models.account.Account;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "review")
@Setter
@Getter
@NoArgsConstructor
public class Review {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private int id;

    @Column(name = "star", nullable = false)
    private int star;

    @Column(name = "content", nullable = false)
    private String content;

    @ManyToOne
    private Account reviewer;

    @ManyToOne
    private Product productReview;
}
