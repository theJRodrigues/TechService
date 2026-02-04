package com.techservice.techservice.config.security;

import com.techservice.techservice.shared.enums.Role;

import java.util.UUID;

public record AuthenticatedAccount(
        UUID id,
        UUID companyID,
        Role role
) {
}
