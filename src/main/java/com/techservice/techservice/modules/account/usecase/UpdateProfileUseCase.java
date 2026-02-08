package com.techservice.techservice.modules.account.usecase;


import com.techservice.techservice.config.security.AuthenticatedAccount;
import com.techservice.techservice.modules.account.domain.Account;
import com.techservice.techservice.modules.account.domain.AccountRepository;
import com.techservice.techservice.modules.account.dto.AccountResponseDTO;
import com.techservice.techservice.modules.account.dto.UpdateProfileRequestDTO;
import com.techservice.techservice.modules.account.mapper.AccountMapper;
import com.techservice.techservice.shared.exceptions.BusinessRuleException;
import com.techservice.techservice.shared.exceptions.NotFoundException;
import com.techservice.techservice.shared.services.AuthenticatedAccountProvider;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
public class UpdateProfileUseCase {
    private final AccountRepository repository;
    private final AuthenticatedAccountProvider authProvider;


    public UpdateProfileUseCase(AccountRepository repository, AuthenticatedAccountProvider authProvider) {
        this.repository = repository;
        this.authProvider = authProvider;
    }

    @Transactional
    public AccountResponseDTO execute(UUID id, UpdateProfileRequestDTO dto) {
        if (dto.name() == null && dto.email() == null) {
            throw new BusinessRuleException("At least one field must be provided");
        }

        AuthenticatedAccount auth = authProvider.get();

        if(dto.email() != null && repository.existsByEmailAndCompanyId(dto.email(), auth.companyId())){
            throw new BusinessRuleException("Email already in use");
        }

        Account acc = repository
                .findByIdAndCompanyId(id, authProvider.get().companyId())
                .orElseThrow(() -> new NotFoundException("Account not found"));

        acc.update(dto.name(), dto.email());

        return AccountMapper.toResponse(acc);
    }
}
