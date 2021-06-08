package com.adpro211.a8.tugaskelompok.wallet.service;

import com.adpro211.a8.tugaskelompok.auths.models.account.Account;
import com.adpro211.a8.tugaskelompok.auths.repository.AccountRepository;
import com.adpro211.a8.tugaskelompok.auths.service.AccountService;
import com.adpro211.a8.tugaskelompok.wallet.models.Transaction;
import com.adpro211.a8.tugaskelompok.wallet.models.Wallet;
import com.adpro211.a8.tugaskelompok.wallet.repository.TransactionRepository;
import com.adpro211.a8.tugaskelompok.wallet.repository.WalletRepository;
import com.adpro211.a8.tugaskelompok.wallet.topup.ATM;
import com.adpro211.a8.tugaskelompok.wallet.topup.CreditCard;
import com.adpro211.a8.tugaskelompok.wallet.topup.Topup;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class WalletServiceImpl implements WalletService {

    @Autowired
    private WalletRepository walletRepository;

    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private AccountRepository accountRepository;

    @Override
    public Wallet createWallet(Account account) {
        Wallet wallet = new Wallet();
        wallet.setAccount(account);
        walletRepository.save(wallet);

        account.setWallet(wallet);
        accountRepository.save(account);
        return wallet;
    }

    @Override
    public Wallet topupWallet(Wallet wallet, String type, Map<String, Object> requestBody) {
        Topup topup;
        switch (type) {
            case "ATM":
                topup = new ATM();
                break;
            default:
                topup = new CreditCard();
                break;
        }
        topup.topup(wallet, requestBody);
        createTransaction(wallet, "Top Up", requestBody);

        return wallet;
    }

    @Override
    public Wallet withdrawWallet(Wallet wallet, Map<String, Object> requestBody) {
        double balance = wallet.getBalance();
        double amount = Double.parseDouble(requestBody.get("amount").toString());

        double currentBal = balance - amount;
        if (currentBal >= 0) {
            wallet.setBalance(currentBal);
            createTransaction(wallet, "Withdraw", requestBody);
            walletRepository.save(wallet);
        }

        return wallet;
    }

    @Override
    public Wallet getWalletById(int id) {
        return walletRepository.findWalletById(id);
    }

    @Override
    public Iterable<Transaction> getTransactionByWallet(Wallet wallet) {
        return transactionRepository.findAllByWallet(wallet);
    }

    public Transaction createTransaction(Wallet wallet, String type, Map<String, Object> requestBody) {
        double amount = Double.parseDouble(requestBody.get("amount").toString());

        Transaction transaction = new Transaction(type, amount);
        transaction.setWallet(wallet);
        transactionRepository.save(transaction);

        return transaction;
    }
}