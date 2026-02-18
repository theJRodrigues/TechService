package com.techservice.techservice.modules.account.domain;

import com.techservice.techservice.modules.company.domain.Company;
import com.techservice.techservice.shared.enums.Role;
import com.techservice.techservice.shared.exceptions.BusinessRuleException;
import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.jetbrains.annotations.NotNull;

import java.time.Instant;
import java.util.UUID;

@Table(name = "accounts")
@Entity
public class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(updatable = false, nullable = false, unique = true)
    private UUID id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String password;

    @Column(name = "is_active", nullable = false)
    private boolean isActive = true;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Role role;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(
            name = "company_id",
            nullable = false
    )
    private Company company;

    @CreationTimestamp
    @Column(name = "created_at", updatable = false, nullable = false)
    private Instant createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at", nullable = false)
    private Instant updatedAt;

    protected Account() {}

    public static Account create(@NotNull String name,
                                 @NotNull String email,
                                 @NotNull String hashedPassword,
                                 @NotNull Role role,
                                 @NotNull Company company) {

        validateNonEmpty(name, "Name");
        validateNonEmpty(email, "Email");
        validateNonEmpty(hashedPassword, "Password");
        validateNonNull(role, "Role");
        validateNonNull(company, "Company");

        Account acc = new Account();
        acc.name = name;
        acc.email = email;
        acc.password = hashedPassword;
        acc.role = role;
        acc.company = company;

        return acc;
    }

    private static void validateNonEmpty(String value, String field){
        if (value == null || value.trim().isEmpty())
            throw new BusinessRuleException(field + " must not be empty");
    }

    private static void validateNonNull  ( Object value, String field){
        if(value == null)
            throw new BusinessRuleException(field + " must not be null");
    }

    public void update(String name, String email) {
        if (name != null) this.name = name;
        if (email != null) this.email = email;
    }

    public void deactivate(){
        if (!this.isActive())
            throw new BusinessRuleException("Account is already inactive");

        this.isActive = false;
    }
    @NotNull public UUID getId() {return id;}
    @NotNull public String getName() {return name;}
    @NotNull public String getEmail() {return email;}
    @NotNull public String getPassword() {return password;}
    public boolean isActive() {return isActive;}
    @NotNull public Role getRole() {return role;}
    @NotNull public Company getCompany() {return company;}
    @NotNull public Instant getCreatedAt() {return createdAt;}
    @NotNull public Instant getUpdatedAt() {return updatedAt;}
}
