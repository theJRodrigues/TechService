package com.techservice.techservice.modules.account.dto;

import com.techservice.techservice.shared.enums.Role;

import java.time.Instant;
import java.util.UUID;

public record AccountResponseRequest(
        UUID id,
        String name,
        String email,
        Boolean isActive,
        Role role,
        Instant createdAt,
        Instant updatedAt
) {
}
