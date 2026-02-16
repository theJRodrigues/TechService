package com.techservice.techservice.modules.account.usecase;


import com.techservice.techservice.config.security.AuthenticatedAccount;
import com.techservice.techservice.modules.account.domain.Account;
import com.techservice.techservice.modules.account.domain.AccountRepository;
import com.techservice.techservice.shared.exceptions.BusinessRuleException;
import com.techservice.techservice.shared.exceptions.ForbiddenException;
import com.techservice.techservice.shared.exceptions.NotFoundException;
import com.techservice.techservice.shared.services.AuthenticatedAccountProvider;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
public class DeactivateAccountByIdUseCase {
    private final AccountRepository repository;
    private final AuthenticatedAccountProvider authProvider;

    public DeactivateAccountByIdUseCase(AccountRepository repository, AuthenticatedAccountProvider authProvider) {
        this.repository = repository;
        this.authProvider = authProvider;
    }

    @Transactional
    public void execute(UUID id) {
        AuthenticatedAccount auth = authProvider.get();

        Account account = repository.findByIdAndCompanyId(id, auth.companyId())
                .orElseThrow(() -> new NotFoundException("The Account could not be found"));

        if (!account.isActive()) {
            throw new BusinessRuleException("Account is already inactive");
        }

        account.setActive(false);
    }
}
