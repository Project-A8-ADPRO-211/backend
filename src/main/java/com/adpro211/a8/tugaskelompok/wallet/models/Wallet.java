package com.adpro211.a8.tugaskelompok.wallet.models;

import com.adpro211.a8.tugaskelompok.auths.models.account.Account;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "wallet")
@Data
public class Wallet {
    @Id
    @GeneratedValue
    @Column(name="id", updatable = false, nullable = false)
    private int id;

    @Column(name="balance", nullable = false)
    private double balance;

    public Wallet() {
        this.balance = 0.0;
    }

    @OneToOne
    @JoinColumn(name = "account_id", referencedColumnName = "id")
    @JsonIgnoreProperties({"wallet"})
    private Account account;

}
