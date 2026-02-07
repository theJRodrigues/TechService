package com.techservice.techservice.modules.account.usecase;

import com.techservice.techservice.config.security.AuthenticatedAccount;
import com.techservice.techservice.modules.account.domain.Account;
import com.techservice.techservice.modules.account.domain.AccountRepository;
import com.techservice.techservice.modules.account.dto.AccountResponseDTO;
import com.techservice.techservice.modules.account.mapper.AccountMapper;
import com.techservice.techservice.shared.services.AuthenticatedAccountProvider;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GetAllAccountsUseCase {
    private final AccountRepository repository;
    private final AuthenticatedAccountProvider authProvider;

    public GetAllAccountsUseCase(AccountRepository repository,
                                 AuthenticatedAccountProvider authProvider) {
        this.repository = repository;
        this.authProvider = authProvider;
    }

    public List<AccountResponseDTO> execute(){
        AuthenticatedAccount auth = authProvider.get();
        List<Account> accounts = repository.findAllByCompanyId(auth.companyId());

        return accounts.stream().map(AccountMapper::toResponse).toList();
    }
}
