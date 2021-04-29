package com.adpro211.a8.tugaskelompok.auths.repository;

import com.adpro211.a8.tugaskelompok.auths.models.account.Account;
import com.adpro211.a8.tugaskelompok.auths.models.authStrategy.AuthStrategy;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AuthStrategyRepository extends JpaRepository<AuthStrategy, String> {
    AuthStrategy findAllByAssignedUser(Account account);
}
