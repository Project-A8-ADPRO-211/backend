package com.adpro211.a8.tugaskelompok.auths.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * This annotation check if a request contain a valid token that belongs to a buyer.
 * This annotation will inject the Buyer or Account object of the user.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.PARAMETER)
public @interface RequireBuyer {
}
