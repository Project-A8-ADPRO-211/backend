package com.adpro211.a8.tugaskelompok.auths.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * This annotation check if a request contain a valid token that belongs to a real user.
 * This annotation will inject the Account object of the user. It won't check the type of the user
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.PARAMETER)
public @interface RequireLoggedIn {
}
