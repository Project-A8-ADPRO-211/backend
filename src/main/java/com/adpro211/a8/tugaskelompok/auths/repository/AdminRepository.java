package com.adpro211.a8.tugaskelompok.auths.repository;

import com.adpro211.a8.tugaskelompok.auths.models.account.Administrator;
import com.adpro211.a8.tugaskelompok.auths.models.account.Buyer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AdminRepository extends JpaRepository<Administrator, Integer> {
    Administrator findAdministratorByEmail(String email);
    Administrator findAdministratorById(int id);
}
