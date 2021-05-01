package com.adpro211.a8.tugaskelompok.wallet.models;

import com.adpro211.a8.tugaskelompok.auths.models.account.Account;
import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "wallet")
@Data
public class Wallet {
    @Id
    @GeneratedValue
    @Column(name="id_wallet", updatable = false, nullable = false)
    private int id_wallet;

    @Column(name="balance", nullable = false)
    private double balance;

    public Wallet() {
        this.balance = 0.0;
    }

    @OneToOne
    private Account account;
}
