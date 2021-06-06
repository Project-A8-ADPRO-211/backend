package com.adpro211.a8.tugaskelompok.wallet.repository;

import com.adpro211.a8.tugaskelompok.wallet.models.Transaction;
import com.adpro211.a8.tugaskelompok.wallet.models.Wallet;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TransactionRepository extends JpaRepository<Transaction, Integer> {
    Iterable<Transaction> findAllByWallet(Wallet wallet);
}
