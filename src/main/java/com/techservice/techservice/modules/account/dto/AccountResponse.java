package com.techservice.techservice.modules.account.dto;

import com.techservice.techservice.shared.enums.Role;

import java.sql.Timestamp;
import java.util.UUID;

public record AccountResponse(
        UUID id,
        String name,
        String email,
        Boolean isActive,
        Role role,
        Timestamp createdAt,
        Timestamp updatedAt
) { }
