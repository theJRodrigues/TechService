package com.techservice.techservice.shared.services;

import com.techservice.techservice.shared.enums.Role;

import java.util.UUID;

public record TokenPayload(UUID id, UUID companyID, Role role) {
}
