package com.techservice.techservice.modules.account.usecase;

import com.techservice.techservice.config.security.AuthenticatedAccount;
import com.techservice.techservice.modules.account.domain.Account;
import com.techservice.techservice.modules.account.domain.AccountRepository;
import com.techservice.techservice.modules.account.dto.AccountResponseDTO;
import com.techservice.techservice.modules.account.dto.CreateAccountRequestDTO;
import com.techservice.techservice.modules.account.mapper.AccountMapper;
import com.techservice.techservice.modules.company.domain.Company;
import com.techservice.techservice.modules.company.domain.CompanyRepository;
import com.techservice.techservice.shared.exceptions.BusinessRuleException;
import com.techservice.techservice.shared.exceptions.InvalidTokenContextException;
import com.techservice.techservice.shared.services.AuthenticatedAccountProvider;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class CreateAccountUseCase {
    private final AccountRepository repository;
    private final PasswordEncoder passwordEncoder;
    private final CompanyRepository companyRepository;
    private final AuthenticatedAccountProvider authProvider;

    public CreateAccountUseCase(AccountRepository repository,
                                PasswordEncoder encoder,
                                CompanyRepository companyRepository,
                                AuthenticatedAccountProvider authProvider) {
        this.repository = repository;
        this.passwordEncoder = encoder;
        this.companyRepository = companyRepository;
        this.authProvider = authProvider;
    }

    public AccountResponseDTO execute(CreateAccountRequestDTO accountDTO) {
        AuthenticatedAccount auth = authProvider.get();

        if (repository.existsByEmailAndCompanyId(accountDTO.email(), auth.companyId())) {
            throw new BusinessRuleException("Email already exists");
        }
        Company company = companyRepository
                .findById(auth.companyId())
                .orElseThrow(InvalidTokenContextException::new);

        String hashedPassword = passwordEncoder.encode(accountDTO.password());

        Account newAccount = Account.create(accountDTO.name(),
                accountDTO.email(),
                hashedPassword,
                accountDTO.role(),
                company);

        Account response = repository.save(newAccount);

        return AccountMapper.toResponse(response);
    }
}
