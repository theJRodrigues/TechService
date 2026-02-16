package com.techservice.techservice.modules.company.domain;

import com.techservice.techservice.modules.account.domain.Account;
import com.techservice.techservice.shared.enums.Role;
import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

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

    @CreationTimestamp
    @Column(nullable = false, name = "created_at", updatable = false)
    private Instant createdAt;

    @UpdateTimestamp
    @Column(nullable = false, name = "updated_at")
    private Instant updatedAt;

    public Company(String name, String CNPJ) {
        this.name = name;
        this.CNPJ = CNPJ;
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

    public UUID getId() {return id;}
    public String getName() {return name;}
    public String getCNPJ() {return CNPJ;}
    public Instant getCreatedAt() {return createdAt;}
    public Instant getUpdatedAt() {return updatedAt;}
}
