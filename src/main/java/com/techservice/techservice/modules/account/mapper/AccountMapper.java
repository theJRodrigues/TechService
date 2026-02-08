package com.techservice.techservice.modules.account.mapper;

import com.techservice.techservice.modules.account.domain.Account;
import com.techservice.techservice.modules.account.dto.AccountResponseDTO;
import com.techservice.techservice.modules.account.dto.CreateAccountRequestDTO;
import com.techservice.techservice.modules.company.domain.Company;

public class AccountMapper {
    public static AccountResponseDTO toResponse(Account account){
        return new AccountResponseDTO(
                account.getId(),
                account.getName(),
                account.getEmail(),
                account.isActive(),
                account.getRole(),
                account.getCreatedAt(),
                account.getUpdatedAt());
    }
}
