package com.adpro211.a8.tugaskelompok.auths.service;

import com.adpro211.a8.tugaskelompok.auths.models.account.Account;

public interface AccountService {

    Account createNewAccount(String name, String email, String password, String type);
    Account updateAccount(int id, Account account);
    Account getAccountById(int id);
    Account getAccountByEmail(String email);

}
