package com.adpro211.a8.tugaskelompok.auths.service;

import com.adpro211.a8.tugaskelompok.auths.models.account.Account;
import com.adpro211.a8.tugaskelompok.auths.models.account.Administrator;
import com.adpro211.a8.tugaskelompok.auths.models.account.Buyer;
import com.adpro211.a8.tugaskelompok.auths.models.account.Seller;
import com.adpro211.a8.tugaskelompok.auths.models.authStrategy.PasswordStrategy;
import com.adpro211.a8.tugaskelompok.auths.repository.AccountRepository;
import com.adpro211.a8.tugaskelompok.auths.repository.PasswordStrategyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AccountServiceImpl implements AccountService {

    @Autowired
    AccountRepository accountRepository;

    @Autowired
    PasswordStrategyRepository passwordStrategyRepository;

    @Override
    public Account createNewAccount(String name, String email, String password, String type) {
        Account account;
        switch (type) {
            case "admin":
                account = new Administrator();
            case "seller":
                account = new Seller();
            default:
                account = new Buyer();
        }
        account.setAccountType(type);
        account.setEmail(email);
        account.setName(name);
        accountRepository.save(account);

        PasswordStrategy passwordStrategy = new PasswordStrategy();
        passwordStrategy.setAssignedUser(account);
        passwordStrategy.setPassword(password);
        passwordStrategyRepository.save(passwordStrategy);

        return account;
    }

    @Override
    public Account updateAccount(int id, Account account) {
        account.setId(id);
        accountRepository.save(account);
        return account;
    }

    @Override
    public Account getAccountById(int id) {
        return accountRepository.findById(id).orElse(null);
    }

    @Override
    public Account getAccountByEmail(String email) {
        return accountRepository.findAccountByEmail(email);
    }
}
