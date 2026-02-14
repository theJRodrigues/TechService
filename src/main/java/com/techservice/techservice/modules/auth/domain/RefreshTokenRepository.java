package com.techservice.techservice.modules.auth.domain;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;


import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

public interface RefreshTokenRepository {

    RefreshToken save(RefreshToken refreshToken);

    Optional<RefreshToken> findByTokenHash(String tokenHash);

    @Modifying
    @Query("""
            update RefreshToken rt
            set rt.revoked = true
            where rt.accountId = :accountId
            and rt.revoked = false
            """)
    int revokeAllByAccountId(UUID accountId);


    Optional<UUID> findAccountIdByTokenHash(String tokenHash);

    @Modifying
    @Query("""
            update RefreshToken rt
            set rt.revoked = true
            where rt.tokenHash = :tokenHash
            and rt.revoked = false
            and rt.expiresAt > :now
            """)
    int revokeIfValid(String tokenHash, Instant now);

    @Modifying
    @Query("""
            update RefreshToken rt
            set rt.revoked = true
            where rt.tokenHash = :tokenHash
            and rt.revoked = false
            """)
    int revokeByTokenHash(String tokenHash);
}
