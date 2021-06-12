package com.adpro211.a8.tugaskelompok.auths.service;

import com.adpro211.a8.tugaskelompok.auths.models.account.Account;
import com.adpro211.a8.tugaskelompok.auths.models.account.Buyer;
import com.adpro211.a8.tugaskelompok.auths.models.authStrategy.AuthStrategy;
import com.adpro211.a8.tugaskelompok.auths.models.authStrategy.PasswordStrategy;
import com.adpro211.a8.tugaskelompok.auths.property.AuthProperty;
import com.adpro211.a8.tugaskelompok.auths.repository.AccountRepository;
import com.adpro211.a8.tugaskelompok.auths.repository.BuyerRepository;
import com.adpro211.a8.tugaskelompok.auths.repository.GoogleOAuthStrategyRepository;
import com.adpro211.a8.tugaskelompok.auths.repository.PasswordStrategyRepository;
import com.adpro211.a8.tugaskelompok.auths.util.OAuthVerifier;
import com.adpro211.a8.tugaskelompok.auths.utls.FakeOAuthVerifier;
import com.adpro211.a8.tugaskelompok.wallet.service.WalletService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.ConfigFileApplicationContextInitializer;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;

import static com.googlecode.catchexception.CatchException.caughtException;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@ContextConfiguration(classes = AuthProperty.class)
class AccountServiceImplTest {

    @InjectMocks
    AccountServiceImpl accountService;

    @Mock
    AccountRepository accountRepository;

    @Mock
    WalletService walletService;

    @Mock
    BuyerRepository buyerRepository;

    @Mock
    PasswordStrategyRepository passwordStrategyRepository;

    @Mock
    GoogleOAuthStrategyRepository googleOAuthStrategyRepository;

    @Mock
    Account accountMock;

    List<AuthStrategy> strategyList = new ArrayList<>();



    @BeforeEach
    void setUp() {
        accountService = new AccountServiceImpl(accountRepository, buyerRepository, passwordStrategyRepository, googleOAuthStrategyRepository, walletService);
        accountService.setUp();
    }

    @Test
    void testCreateAccountsSuccess() {
        when(walletService.createWallet(any())).thenReturn(null);

        accountService.createAccount("a", "abc@def.com", "admin");
        accountService.createAccount("add", "dd@def.com", "buyer");
        accountService.createAccount("add", "absdadc@def.com", "seller");
        verify(accountRepository, times(3)).save(any());

    }

    @Test
    void testCreateAccountFullSuccess() {
        when(walletService.createWallet(any())).thenReturn(null);

        accountService.createNewAccount("aa", "abc@def.com", "test", "admin");

        verify(accountRepository, times(1)).save(any());
        verify(passwordStrategyRepository, times(1)).save(any());

    }

    @Test
    void testUpdateAccount() {

        Buyer buyer = new Buyer();

        accountService.updateAccount(1, buyer);

        verify(accountRepository, times(1)).save(buyer);

    }

    @Test
    void testUpdateBuyer() {

        Buyer buyer = new Buyer();

        accountService.updateBuyer(buyer);

        verify(buyerRepository, times(1)).save(buyer);

    }

    @Test
    void testOAuthVerifier() {
        accountService.setVerifier(null);
        assertEquals(OAuthVerifier.class, accountService.getVerifier().getClass());
    }

    @Test
    void testGetByEmail() {

        Buyer buyer = new Buyer();

        when(accountRepository.findAccountByEmail(any())).thenReturn(buyer);

        assertEquals(buyer, accountService.getAccountByEmail("abc@def.com"));

    }

    @Test
    void testGetById() {

        Buyer buyer = new Buyer();

        when(accountRepository.findById(any())).thenReturn(Optional.of(buyer));

        assertEquals(buyer, accountService.getAccountById(1));

    }

    @Test
    void testCreateAccountFullInvalidEmail() {

        boolean exceptionRaised = false;

        try {
            accountService.createNewAccount("aa", "abcdef.com", "test", "admin");
        } catch (ResponseStatusException e) {
            exceptionRaised = true;
        }

        assertTrue(exceptionRaised);
    }

    @Test
    void testCreateAccountAdminFailMultipleEmail() {
        given(accountRepository.save(any())).willThrow(new DataIntegrityViolationException("aaa"));
        boolean exceptionHappened = false;
        try {
            BDDMockito.when(accountService.createAccount("a", "abc@def.com", "admin"));
        } catch (ResponseStatusException e) {
            exceptionHappened = true;
        }
        assertTrue(exceptionHappened);
    }

    @Test
    void testChangeAccountPasswordSuccess() {
        PasswordStrategy passwordStrategy = Mockito.mock(PasswordStrategy.class);

        strategyList.clear();
        strategyList.add(passwordStrategy);

        when(accountMock.getAuthStrategies()).thenReturn(new HashSet<>(strategyList));
        when(passwordStrategy.getStrategyName()).thenReturn("password");

        accountService.updateAccountPass(accountMock, "12345");

        verify(passwordStrategy, times(1)).setPassword("12345");
    }

    @Test
    void testChangeAccountPasswordFailNoPasswordStrategy() {
        PasswordStrategy otherStrategy = Mockito.mock(PasswordStrategy.class);

        strategyList.clear();
        strategyList.add(otherStrategy);

        when(accountMock.getAuthStrategies()).thenReturn(new HashSet<>(strategyList));
        when(otherStrategy.getStrategyName()).thenReturn("otherStrategy");

        assertNull(accountService.updateAccountPass(accountMock, "12345"));
    }

    @Test
    void testCreateAccountGoogleFail() {
        OAuthVerifier.construct("s");
        accountService.setVerifier(OAuthVerifier.getInstance());

        boolean exceptionHappened = false;
        try {
            accountService.createNewAccountGoogle("a", "admin");
        } catch (ResponseStatusException e) {
            exceptionHappened = true;
        }
        assertTrue(exceptionHappened);

    }

    @Test
    void testCreateAccountGoogle() {
        OAuthVerifier.GoogleOAuthData data = new OAuthVerifier.GoogleOAuthData("a", "a@bdd.com", "1");
        accountService.setVerifier(new FakeOAuthVerifier(data));

        accountService.createNewAccountGoogle("a", "admin");

        verify(accountRepository, times(1)).save(any());

    }
}