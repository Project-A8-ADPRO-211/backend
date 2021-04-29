package com.adpro211.a8.tugaskelompok.auths.repository;

import com.adpro211.a8.tugaskelompok.auths.models.account.Account;
import com.adpro211.a8.tugaskelompok.auths.models.account.Buyer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BuyerRepository extends JpaRepository<Buyer, String> {
    Buyer findBuyerByEmail(String email);
    Buyer findBuyerById(int id);
}
