package com.gestaoTI.gestaoTI.modules.account.dto;

import com.gestaoTI.gestaoTI.shared.enums.Role;

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
