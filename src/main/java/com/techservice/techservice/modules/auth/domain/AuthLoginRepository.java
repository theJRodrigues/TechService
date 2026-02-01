package com.techservice.techservice.modules.auth.domain;

import com.techservice.techservice.modules.account.domain.Account;

import java.util.Optional;

public interface AuthLoginRepository {
    Optional<Account> findByEmail(String email);
}
