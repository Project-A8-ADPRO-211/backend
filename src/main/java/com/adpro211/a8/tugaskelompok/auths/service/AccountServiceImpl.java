package com.adpro211.a8.tugaskelompok.auths.service;

import com.adpro211.a8.tugaskelompok.auths.models.account.Account;
import com.adpro211.a8.tugaskelompok.auths.models.account.Administrator;
import com.adpro211.a8.tugaskelompok.auths.models.account.Buyer;
import com.adpro211.a8.tugaskelompok.auths.models.account.Seller;
import com.adpro211.a8.tugaskelompok.auths.models.authStrategy.AuthStrategy;
import com.adpro211.a8.tugaskelompok.auths.models.authStrategy.GoogleOauthStrategy;
import com.adpro211.a8.tugaskelompok.auths.models.authStrategy.PasswordStrategy;
import com.adpro211.a8.tugaskelompok.auths.repository.AccountRepository;
import com.adpro211.a8.tugaskelompok.auths.repository.BuyerRepository;
import com.adpro211.a8.tugaskelompok.auths.repository.GoogleOAuthStrategyRepository;
import com.adpro211.a8.tugaskelompok.auths.repository.PasswordStrategyRepository;
import com.adpro211.a8.tugaskelompok.auths.util.OAuthVerifier;
import com.adpro211.a8.tugaskelompok.wallet.models.Wallet;
import com.adpro211.a8.tugaskelompok.wallet.repository.WalletRepository;
import com.adpro211.a8.tugaskelompok.wallet.service.WalletService;
import org.apache.commons.validator.routines.EmailValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import javax.annotation.PostConstruct;

@Service
public class AccountServiceImpl implements AccountService {

    @Autowired
    WalletService walletService;

    @Autowired
    WalletRepository walletRepository;

    public OAuthVerifier getVerifier() {
        if (verifier == null) {
            verifier = OAuthVerifier.getInstance();
        }
        return verifier;
    }

    public void setVerifier(OAuthVerifier verifier) {
        this.verifier = verifier;
    }

    OAuthVerifier verifier;

    AccountRepository accountRepository;

    BuyerRepository buyerRepository;

    PasswordStrategyRepository passwordStrategyRepository;

    GoogleOAuthStrategyRepository googleOAuthStrategyRepository;

    @Autowired
    public AccountServiceImpl(@Autowired AccountRepository accountRepository,
                              @Autowired BuyerRepository buyerRepository,
                              @Autowired PasswordStrategyRepository passwordStrategyRepository,
                              @Autowired GoogleOAuthStrategyRepository googleOAuthStrategyRepository) {
        this.accountRepository = accountRepository;
        this.buyerRepository = buyerRepository;
        this.passwordStrategyRepository = passwordStrategyRepository;
        this.googleOAuthStrategyRepository = googleOAuthStrategyRepository;
    }

    EmailValidator emailValidator;

    @PostConstruct
    public void setUp() {
        emailValidator = EmailValidator.getInstance(true);
    }

    @Override
    public Account createNewAccount(String name, String email, String password, String type) {

        if (!emailValidator.isValid(email)) throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid email");

        Account account = createAccount(name, email, type);

        PasswordStrategy passwordStrategy = new PasswordStrategy();
        passwordStrategy.setAssignedUser(account);
        passwordStrategy.setPassword(password);
        passwordStrategyRepository.save(passwordStrategy);

        return account;
    }

    @Override
    public Account createNewAccountGoogle(String token, String accType) {
        OAuthVerifier.GoogleOAuthData payload = getVerifier().verifyToken(token);

        Account account = createAccount(payload.getName(), payload.getEmail(), accType);

        GoogleOauthStrategy googleOauthStrategy = new GoogleOauthStrategy();
        googleOauthStrategy.setAssignedUser(account);
        googleOauthStrategy.setUid(payload.getUid());
        googleOAuthStrategyRepository.save(googleOauthStrategy);

        return account;
    }


    public Account createAccount(String name, String email, String type) {
        Account account;
        switch (type) {
            case "admin":
                account = new Administrator();
                break;
            case "seller":
                account = new Seller();
                break;
            default:
                account = new Buyer();
                break;
        }
        account.setAccountType(type);
        account.setEmail(email);
        account.setName(name);
        try {
            accountRepository.save(account);
        } catch (DataIntegrityViolationException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Duplicate email");
        }
        walletService.createWallet(account);
        return account;
    }

    @Override
    public Account updateAccountPass(Account account, String newPassword) {
        for (AuthStrategy strategy : account.getAuthStrategies()) {
            if (!strategy.getStrategyName().equals("password")) continue;

            PasswordStrategy passwordStrategy = (PasswordStrategy) strategy;
            passwordStrategy.setPassword(newPassword);
            passwordStrategyRepository.save(passwordStrategy);
            return account;

        }
        return null;
    }

    @Override
    public Account updateAccount(int id, Account account) {
        account.setId(id);
        accountRepository.save(account);
        return account;
    }

    @Override
    public Buyer updateBuyer(Buyer account) {
        buyerRepository.save(account);
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
