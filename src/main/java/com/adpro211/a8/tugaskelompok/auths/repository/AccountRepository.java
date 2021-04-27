package com.adpro211.a8.tugaskelompok.auths.repository;

import com.adpro211.a8.tugaskelompok.auths.models.account.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountRepository extends JpaRepository<Account, String> {
    Account findAccountByEmail(String email);
}
