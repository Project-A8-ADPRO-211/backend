package com.adpro211.a8.tugaskelompok.auths.service;

import com.adpro211.a8.tugaskelompok.auths.models.account.Account;
import com.adpro211.a8.tugaskelompok.auths.models.account.Buyer;

public interface AccountService {

    Account createNewAccount(String name, String email, String password, String type);
    Account createNewAccountGoogle(String token, String accType);
    Account createAccount(String name, String email, String type);
    Account updateAccount(int id, Account account);
    Account getAccountById(int id);
    Account getAccountByEmail(String email);
    Buyer updateBuyer(Buyer account);
    Account updateAccountPass(Account account, String newPassword);

}
