package com.techservice.techservice.modules.account.usecase;

import com.techservice.techservice.config.security.AuthenticatedAccount;
import com.techservice.techservice.modules.account.domain.Account;
import com.techservice.techservice.modules.account.domain.AccountRepository;
import com.techservice.techservice.modules.account.dto.AccountResponse;
import com.techservice.techservice.modules.account.dto.CreateAccountRequest;
import com.techservice.techservice.modules.account.mapper.AccountMapper;
import com.techservice.techservice.modules.company.domain.Company;
import com.techservice.techservice.modules.company.domain.CompanyRepository;
import com.techservice.techservice.shared.exceptions.EmailAlreadyExistsException;
import com.techservice.techservice.shared.exceptions.InvalidTokenContextException;
import com.techservice.techservice.shared.services.AuthenticatedAccountProvider;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class CreateAccountUseCase {
    private final AccountRepository repository;
    private final PasswordEncoder passwordEncoder;
    private final CompanyRepository companyRepository;

    public CreateAccountUseCase(AccountRepository repository, PasswordEncoder encoder, CompanyRepository companyRepository) {
        this.repository = repository;
        this.passwordEncoder = encoder;
        this.companyRepository = companyRepository;
    }

    public AccountResponse execute(CreateAccountRequest accountDTO) {
        if (repository.existsByEmail(accountDTO.email())) {
            throw new EmailAlreadyExistsException();
        }

        AuthenticatedAccount auth = AuthenticatedAccountProvider.get();

        Company company = companyRepository
                .findById(auth.companyId())
                .orElseThrow(InvalidTokenContextException::new);

        String hashedPassword = passwordEncoder.encode(accountDTO.password());

        Account newAccount = AccountMapper.fromCreate(accountDTO, hashedPassword, company);

        Account response = repository.save(newAccount);
        return AccountMapper.toResponse(response);
    }
}
