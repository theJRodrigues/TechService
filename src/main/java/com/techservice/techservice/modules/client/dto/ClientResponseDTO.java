package com.techservice.techservice.modules.client.dto;

import com.techservice.techservice.shared.valueObjects.Address;

import java.time.Instant;
import java.util.UUID;

public record ClientResponseDTO(
        UUID id,
        String name,
        String phone,
        String email,
        String documentType,
        String document,
        Address address,
        Boolean isActive,
        Instant createdAt,
        Instant updatedAt
) {
}
