package com.techservice.techservice.modules.company.usecase;

import com.techservice.techservice.modules.account.domain.Account;
import com.techservice.techservice.modules.account.domain.AccountRepository;
import com.techservice.techservice.modules.company.domain.Company;
import com.techservice.techservice.modules.company.domain.CompanyRepository;
import com.techservice.techservice.modules.company.dto.CompanyResponseRequest;
import com.techservice.techservice.modules.company.dto.CreateCompanyRequest;
import com.techservice.techservice.modules.company.mapper.CompanyMapper;
import com.techservice.techservice.shared.enums.Role;
import com.techservice.techservice.shared.exceptions.CNPJAlreadyExistsException;
import com.techservice.techservice.shared.exceptions.EmailAlreadyExistsException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CreateCompanyUseCase {
    private final CompanyRepository repository;
    private final AccountRepository accountRepository;
    private final PasswordEncoder passwordEncoder;

    public CreateCompanyUseCase(CompanyRepository repository,
                                AccountRepository accountRepository,
                                PasswordEncoder encoder) {
        this.repository = repository;
        this.accountRepository = accountRepository;
        this.passwordEncoder = encoder;
    }

    @Transactional
    public CompanyResponseRequest execute(CreateCompanyRequest dto) {
        if(repository.existsByCNPJ(dto.CNPJ())){
            throw new CNPJAlreadyExistsException();
        }
        Company company = CompanyMapper.toEntity(dto);

        if(accountRepository.existsByEmail(dto.adminEmail())){
            throw new EmailAlreadyExistsException();
        }

        Account admAccount = new Account(dto.adminName(),
                dto.adminEmail(),
                dto.adminPassword(),
                Role.ADMIN);
        admAccount.setPassword(passwordEncoder.encode(admAccount.getPassword()));
        repository.save(company);
        accountRepository.save(admAccount);

        return new CompanyResponseRequest(company.getId(), company.getName());
    }

}
