package com.adpro211.a8.tugaskelompok.auths.resolver;

import com.adpro211.a8.tugaskelompok.auths.models.account.Account;
import com.adpro211.a8.tugaskelompok.auths.models.account.Administrator;
import com.adpro211.a8.tugaskelompok.auths.repository.AdminRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class AdminAccountFromTokenResolver extends BaseAccountFromTokenResolver {
    @Autowired
    AdminRepository adminRepository;

    @Override
    protected Class getAccountTypeClass() {
        return Administrator.class;
    }

    @Override
    protected boolean checkAccount(Account account) {
        return account.getAccountType().equals("admin");
    }

    @Override
    protected Object createAccountSubtype(Account account) {
        return adminRepository.findAdministratorById(account.getId());
    }
}
