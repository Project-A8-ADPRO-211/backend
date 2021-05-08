package com.adpro211.a8.tugaskelompok.auths.repository;

import com.adpro211.a8.tugaskelompok.auths.models.account.Account;
import com.adpro211.a8.tugaskelompok.auths.models.authStrategy.GoogleOauthStrategy;
import com.adpro211.a8.tugaskelompok.auths.models.authStrategy.PasswordStrategy;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GoogleOAuthStrategyRepository extends JpaRepository<GoogleOauthStrategy, String> {
    GoogleOauthStrategy findAllByAssignedUser(Account account);
}
