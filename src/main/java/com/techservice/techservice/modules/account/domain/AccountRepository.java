package com.techservice.techservice.modules.account.domain;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface AccountRepository {
    Account save(Account account);

    boolean existsByEmail(String email);

    List<Account> findAll();

    Optional<Account> findById(UUID id);

    void deleteById(UUID id);
}

