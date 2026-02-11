package com.techservice.techservice.modules.auth.infra.persistence;

import com.techservice.techservice.modules.account.domain.Account;
import com.techservice.techservice.modules.auth.domain.AuthRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface JPAAuthRepository extends JpaRepository<Account, UUID>, AuthRepository {
}
