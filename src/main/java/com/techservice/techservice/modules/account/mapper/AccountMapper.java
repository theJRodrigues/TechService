package com.techservice.techservice.modules.account.mapper;

import com.techservice.techservice.modules.account.domain.Account;
import com.techservice.techservice.modules.account.dto.AccountResponseRequest;
import com.techservice.techservice.modules.account.dto.CreateAccountRequest;
import com.techservice.techservice.modules.company.domain.Company;

public class AccountMapper {
    public static Account fromCreate(CreateAccountRequest dto, String hashedPassword, Company company){
        return new Account(
                dto.name(),
                dto.email(),
                hashedPassword,
                dto.role(),
                company
        );
    }

    public static AccountResponseRequest toResponse(Account account){
        return new AccountResponseRequest(
                account.getId(),
                account.getName(),
                account.getEmail(),
                account.isActive(),
                account.getRole(),
                account.getCreatedAt(),
                account.getUpdatedAt());
    }
}
