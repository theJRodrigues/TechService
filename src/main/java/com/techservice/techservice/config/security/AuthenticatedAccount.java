package com.techservice.techservice.config.security;

import java.util.UUID;

public record AuthenticatedAccount(
        UUID id,
        String email,
        String role
) {
}
