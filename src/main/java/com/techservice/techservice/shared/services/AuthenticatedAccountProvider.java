package com.techservice.techservice.shared.services;

import com.techservice.techservice.config.security.AuthenticatedAccount;
import org.springframework.security.core.context.SecurityContextHolder;

public final class AuthenticatedAccountProvider {
    private AuthenticatedAccountProvider(){}

    public static AuthenticatedAccount get(){
        var auth = SecurityContextHolder.getContext().getAuthentication();

        if(auth == null || !(auth.getPrincipal() instanceof AuthenticatedAccount principal)){
            throw new IllegalStateException("User not authenticated");
        }
        return principal;
    }
}
