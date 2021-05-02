package com.adpro211.a8.tugaskelompok.auths.resolver;

import com.adpro211.a8.tugaskelompok.auths.annotation.RequireLoggedIn;
import com.adpro211.a8.tugaskelompok.auths.models.account.Account;
import com.adpro211.a8.tugaskelompok.auths.service.AccountService;
import com.adpro211.a8.tugaskelompok.auths.service.JWTService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.MethodParameter;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;
import org.springframework.web.server.ResponseStatusException;

@Component
public class BaseAccountFromTokenResolver implements HandlerMethodArgumentResolver {

    private AccountService accountService;
    private JWTService jwtService;

    @Autowired
    public final void setAccountService(AccountService accountService) {
        this.accountService = accountService;
    }

    @Autowired
    public final void setJwtService(JWTService jwtService) {
        this.jwtService = jwtService;
    }

    protected Class getAnnotationClass() {
        return RequireLoggedIn.class;
    }

    protected Class getAccountSubtypeClass() {
        return Account.class;
    }

    @Override
    public final boolean supportsParameter(MethodParameter parameter) {
        return parameter.getParameterAnnotation(getAnnotationClass()) != null &&
                (parameter.getParameterType().equals(getAccountSubtypeClass()) ||
                                parameter.getParameterType().equals(Account.class));
    }

    protected boolean checkAccount(Account account) {
        return true;
    }

    protected Object createAccountSubtype(Account account) {
        return (Object) account;
    }

    @Override
    public final Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer,
                                        NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
        String authorizationHeader = webRequest.getHeader("Authorization");
        if (authorizationHeader == null) throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        String decodedToken = jwtService.verifyToken(authorizationHeader);
        if (decodedToken == null) throw new ResponseStatusException(HttpStatus.FORBIDDEN, authorizationHeader);
        int accountId;
        try {
            accountId = Integer.parseInt(decodedToken);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        Account account = accountService.getAccountById(accountId);
        if (account == null) throw new ResponseStatusException(HttpStatus.FORBIDDEN, "d");
        if (!checkAccount(account)) throw new ResponseStatusException(HttpStatus.FORBIDDEN, "c");
        return createAccountSubtype(account);
    }
}
