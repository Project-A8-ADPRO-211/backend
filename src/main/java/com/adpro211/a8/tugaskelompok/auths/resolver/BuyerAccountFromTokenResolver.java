package com.adpro211.a8.tugaskelompok.auths.resolver;

import com.adpro211.a8.tugaskelompok.auths.annotation.RequireBuyer;
import com.adpro211.a8.tugaskelompok.auths.models.account.Account;
import com.adpro211.a8.tugaskelompok.auths.models.account.Buyer;
import com.adpro211.a8.tugaskelompok.auths.repository.BuyerRepository;
import com.adpro211.a8.tugaskelompok.auths.service.AccountService;
import com.adpro211.a8.tugaskelompok.auths.service.JWTService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class BuyerAccountFromTokenResolver extends BaseAccountFromTokenResolver {
    @Autowired
    BuyerRepository buyerRepository;

    @Override
    protected Class getAnnotationClass() {
        return RequireBuyer.class;
    }

    @Override
    protected Class getAccountSubtypeClass() {
        return Buyer.class;
    }


    @Override
    protected boolean checkAccount(Account account) {
        return account.getAccountType().equals("buyer");
    }

    @Override
    protected Object createAccountSubtype(Account account) {
        return buyerRepository.findBuyerById(account.getId());
    }
}
