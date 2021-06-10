package com.adpro211.a8.tugaskelompok.wallet.models;

import com.adpro211.a8.tugaskelompok.auths.models.account.Account;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.*;

@Entity
@Table(name = "transaction")
@Getter
@Setter
@NoArgsConstructor
public class Transaction {
    @Id
    @GeneratedValue
    @Column(name="id", updatable = false, nullable = false)
    private int id;

    @Column(name="type", nullable = false)
    private String type;

    @Column(name="amount", nullable = false)
    private double amount;

    @Column(name="datetime", nullable = false)
    private LocalDateTime dateTime;

    public Transaction(String type, double amount) {
        this.type = type;
        this.amount = amount;
        this.dateTime = LocalDateTime.now();
    }

    @JsonBackReference
    @ManyToOne
    @JoinColumn(name = "wallet_id", referencedColumnName = "id")
    private Wallet wallet;
}
