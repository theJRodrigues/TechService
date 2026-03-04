package com.techservice.techservice.modules.company.domain;

import com.techservice.techservice.modules.account.domain.Account;
import com.techservice.techservice.shared.enums.Role;
import com.techservice.techservice.shared.exceptions.BusinessRuleException;
import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.jetbrains.annotations.NotNull;

import java.time.Instant;
import java.util.UUID;

@Table(name = "companies")
@Entity
public class Company {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(updatable = false, nullable = false, unique = true)
    private UUID id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String CNPJ;

    @Column(nullable = false, name = "is_active")
    private Boolean isActive = true;

    @CreationTimestamp
    @Column(nullable = false, name = "created_at", updatable = false)
    private Instant createdAt;

    @UpdateTimestamp
    @Column(nullable = false, name = "updated_at")
    private Instant updatedAt;

    public Company(@NotNull String name,@NotNull String CNPJ) {
        if(name == null || name.trim().isEmpty()){
            throw new BusinessRuleException("Company name must not be empty");
        }

        String normalizedCnpj = CNPJ.replaceAll("\\D", "").trim();

        if(CNPJ == null || normalizedCnpj.length() != 14){
            throw new BusinessRuleException("Invalid CNPJ");
        }

        this.name = name.trim();
        this.CNPJ = normalizedCnpj;
    }

    protected Company(){};

    public Account createAdmin(String name,
                               String email,
                               String password) {
        return Account
                .create(name,
                email,
                password,
                Role.ADMIN,
                this);
    }

    @NotNull public UUID getId() {return id;}
    @NotNull public String getName() {return name;}
    @NotNull public String getCNPJ() {return CNPJ;}
    @NotNull public Instant getCreatedAt() {return createdAt;}
    @NotNull public Instant getUpdatedAt() {return updatedAt;}
    @NotNull public Boolean isActive() {return isActive;}
}
