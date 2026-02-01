package com.techservice.techservice.modules.auth.infra.persistence;

import com.techservice.techservice.modules.account.domain.Account;
import com.techservice.techservice.modules.auth.domain.AuthLoginRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface JPAAuthLoginRepository extends JpaRepository<Account, UUID>, AuthLoginRepository {
}
