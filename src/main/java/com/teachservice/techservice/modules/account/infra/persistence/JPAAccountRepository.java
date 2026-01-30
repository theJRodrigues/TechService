package com.teachservice.techservice.modules.account.infra.persistence;

import com.teachservice.techservice.modules.account.domain.Account;
import com.teachservice.techservice.modules.account.domain.AccountRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface JPAAccountRepository extends JpaRepository<Account, UUID>, AccountRepository {
}
