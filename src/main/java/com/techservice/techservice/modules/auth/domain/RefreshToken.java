package com.techservice.techservice.modules.auth.domain;

import com.techservice.techservice.shared.enums.Role;
import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.Duration;
import java.time.Instant;
import java.util.UUID;

@Entity
@Table(name = "refresh_tokens")
public class RefreshToken {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false, name = "account_id")
    private UUID accountId;

    @Column(nullable = false, name = "company_id")
    private UUID companyId;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Role role;

    @Column(nullable = false, unique = true, name = "token_hash")
    private String tokenHash;

    @Column(nullable = false, name = "expires_at")
    private Instant expiresAt;

    @Column(nullable = false)
    private boolean revoked;

    @Column(nullable = false, name = "created_at", updatable = false)
    @CreationTimestamp
    private Instant createdAt;

    protected RefreshToken() {
    }

    public static RefreshToken create(UUID accountId,
                                      UUID companyId,
                                      Role role,
                                      String tokenHash) {
        RefreshToken rt = new RefreshToken();
        rt.accountId = accountId;
        rt.companyId = companyId;
        rt.role = role;
        rt.tokenHash = tokenHash;
        rt.expiresAt = Instant.now().plus(Duration.ofDays(30));

        return rt;
    }

    public boolean isExpired() {
        return Instant.now().isAfter(expiresAt);
    }

    public boolean isValid() {
        return !revoked && !isExpired();
    }

    public void revoke() {
        this.revoked = true;
    }

    public UUID getCompanyId() {
        return companyId;
    }

    public Role getRole() {
        return role;
    }

    public UUID getId() {
        return id;
    }

    public UUID getAccountId() {
        return accountId;
    }

    public String getTokenHash() {
        return tokenHash;
    }

    public Instant getExpiresAt() {
        return expiresAt;
    }

    public boolean isRevoked() {
        return revoked;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }
}
