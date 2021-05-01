package com.adpro211.a8.tugaskelompok.wallet.repository;

import com.adpro211.a8.tugaskelompok.wallet.models.Wallet;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WalletRepository extends JpaRepository<Wallet, Integer> {
    Wallet findWalletById(int id);
}
