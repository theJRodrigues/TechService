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
        Account account = repository.findById(id)
                .orElseThrow(() -> new NotFoundException("The Account could not be found"));

        AuthenticatedAccount auth = authProvider.get();

        if (!account.getCompany().getId().equals(auth.companyId())) {
            throw new ForbiddenException("You do not have permission to manage accounts from another company");
        }

        if (!account.isActive()) {
            throw new BusinessRuleException("Account is already inactive");
        }

        account.setActive(false);
    }
}
