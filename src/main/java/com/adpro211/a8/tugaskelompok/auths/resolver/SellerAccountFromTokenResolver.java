package com.adpro211.a8.tugaskelompok.auths.resolver;

import com.adpro211.a8.tugaskelompok.auths.models.account.Account;
import com.adpro211.a8.tugaskelompok.auths.models.account.Seller;
import com.adpro211.a8.tugaskelompok.auths.repository.SellerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class SellerAccountFromTokenResolver extends BaseAccountFromTokenResolver {
    @Autowired
    SellerRepository sellerRepository;


    @Override
    protected Class getAccountTypeClass() {
        return Seller.class;
    }

    @Override
    protected boolean checkAccount(Account account) {
        return account.getAccountType().equals("seller");
    }

    @Override
    protected Object createAccountSubtype(Account account) {
        return sellerRepository.findSellerById(account.getId());
    }
}
