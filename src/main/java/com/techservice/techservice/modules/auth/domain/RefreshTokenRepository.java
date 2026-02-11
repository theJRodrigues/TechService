package com.techservice.techservice.modules.auth.domain;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;


import java.util.Optional;
import java.util.UUID;

public interface RefreshTokenRepository {

    RefreshToken save(RefreshToken refreshToken);
    Optional<RefreshToken> findByTokenHash(String tokenHash);

    void findAllValidByAccountId(UUID accountId);

    @Modifying
    @Query("""
            update RefreshToken rt
            set rt.revoked = true
            where rt.accountId = :accountId
            """)
    void revokeAllByAccountId(UUID id);
}
