package com.adpro211.a8.tugaskelompok.wallet.models;

import com.adpro211.a8.tugaskelompok.auths.models.account.Account;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "transaction")
@Data
public class Transaction {
    @Id
    @GeneratedValue
    @Column(name="id", updatable = false, nullable = false)
    private int id;

    @Column(name="amount", nullable = false)
    private double amount;

    @Column(name="date", nullable = false)
    private Date date;

    public Transaction(double amount) {
        this.amount = amount;
        this.date = new Date();
    }

    @OneToOne
    @JoinColumn(name = "account_id", referencedColumnName = "id")
    @JsonIgnoreProperties({"wallet"})
    private Account account;
}
