package com.techservice.techservice.modules.account.domain;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface AccountRepository {
    Account save(Account account);

    boolean existsByEmailAndCompanyId(String email, UUID companyId);

    boolean existsByEmail(String email);

    Optional<Account> findByIdAndCompanyId(UUID id, UUID companyId);

    List<Account> findAllByCompanyIdAndIsActiveTrue(UUID companyId);

    boolean existsByIdAndCompanyIdAndIsActiveTrue(UUID id, UUID companyId);
}

