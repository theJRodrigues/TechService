package com.gestaoTI.gestaoTI.modules.account.mapper;

import com.gestaoTI.gestaoTI.modules.account.domain.Account;
import com.gestaoTI.gestaoTI.modules.account.dto.AccountResponse;
import com.gestaoTI.gestaoTI.modules.account.dto.CreateAccountRequest;

public class AccountMapper {
    public static Account fromCreate(CreateAccountRequest dto){
        return new Account(
                dto.name(),
                dto.email(),
                dto.password(),
                dto.role()
        );
    }

    public static AccountResponse toResponse(Account account){
        return new AccountResponse(
                account.getId(),
                account.getName(),
                account.getEmail(),
                account.isActive(),
                account.getRole(),
                account.getCreatedAt(),
                account.getUpdatedAt());
    }
}
