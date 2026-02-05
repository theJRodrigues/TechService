package com.techservice.techservice.shared.services;

import com.techservice.techservice.config.security.AuthenticatedAccount;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
public  class AuthenticatedAccountProvider {
    public AuthenticatedAccountProvider(){}

    public AuthenticatedAccount get(){
        var auth = SecurityContextHolder.getContext().getAuthentication();

        if(auth == null || !(auth.getPrincipal() instanceof AuthenticatedAccount principal)){
            throw new IllegalStateException("User not authenticated");
        }
        return principal;
    }
}
