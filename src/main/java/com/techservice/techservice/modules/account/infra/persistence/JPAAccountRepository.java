package com.techservice.techservice.modules.account.infra.persistence;

import com.techservice.techservice.modules.account.domain.Account;
import com.techservice.techservice.modules.account.domain.AccountRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface JPAAccountRepository extends JpaRepository<Account, UUID>, AccountRepository {
}
