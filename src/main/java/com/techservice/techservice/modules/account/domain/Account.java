package com.techservice.techservice.modules.account.domain;

import com.techservice.techservice.modules.company.domain.Company;
import com.techservice.techservice.shared.enums.Role;
import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

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

    public static Account create(String name,
                                 String email,
                                 String hashedPassword,
                                 Role role,
                                 Company company) {
        Account acc = new Account();
        acc.name = name;
        acc.email = email;
        acc.password = hashedPassword;
        acc.role = role;
        acc.company = company;

        return acc;
    }

    public void update(String name, String email) {
        if (name != null) this.name = name;
        if (email != null) this.email = email;
    }

    public UUID getId() {return id;}
    public String getName() {return name;}
    public String getEmail() {return email;}
    public String getPassword() {return password;}
    public boolean isActive() {return isActive;}
    public Role getRole() {return role;}
    public Company getCompany() {return company;}
    public Instant getCreatedAt() {return createdAt;}
    public Instant getUpdatedAt() {return updatedAt;}
}
