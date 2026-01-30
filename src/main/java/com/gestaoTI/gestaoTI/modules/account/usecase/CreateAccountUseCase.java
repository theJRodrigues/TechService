package com.gestaoTI.gestaoTI.modules.account.usecase;

import com.gestaoTI.gestaoTI.modules.account.domain.Account;
import com.gestaoTI.gestaoTI.modules.account.domain.AccountRepository;
import com.gestaoTI.gestaoTI.modules.account.dto.AccountResponse;
import com.gestaoTI.gestaoTI.modules.account.dto.CreateAccountRequest;
import com.gestaoTI.gestaoTI.modules.account.mapper.AccountMapper;
import com.gestaoTI.gestaoTI.shared.exceptions.EmailAlreadyExistsException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class CreateAccountUseCase {
    private final AccountRepository repository;
    private final BCryptPasswordEncoder passwordEncoder;

    public CreateAccountUseCase(AccountRepository repository){
        this.repository = repository;
        this.passwordEncoder = new BCryptPasswordEncoder();
    }

    public AccountResponse execute(CreateAccountRequest accountDTO){

        if(repository.existsByEmail(accountDTO.email())){
            throw new EmailAlreadyExistsException();
        }

            Account newAccount = AccountMapper.fromCreate(accountDTO);
            newAccount.setPassword(passwordEncoder.encode(newAccount.getPassword()));
            Account response = repository.save(newAccount);
            return AccountMapper.toResponse(response);


    }
}
