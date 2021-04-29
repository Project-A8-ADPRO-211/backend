package com.adpro211.a8.tugaskelompok.auths.repository;

import com.adpro211.a8.tugaskelompok.auths.models.account.Buyer;
import com.adpro211.a8.tugaskelompok.auths.models.account.Seller;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SellerRepository extends JpaRepository<Seller, String> {
    Seller findSellerByEmail(String email);
    Seller findSellerById(int id);
}
