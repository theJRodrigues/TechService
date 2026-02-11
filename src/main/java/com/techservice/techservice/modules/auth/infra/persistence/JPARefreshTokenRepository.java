package com.techservice.techservice.modules.auth.infra.persistence;

import com.techservice.techservice.modules.auth.domain.RefreshToken;
import com.techservice.techservice.modules.auth.domain.RefreshTokenRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface JPARefreshTokenRepository extends JpaRepository<RefreshToken, UUID>, RefreshTokenRepository {
}
