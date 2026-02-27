package com.techservice.techservice.modules.client.dto;

import com.techservice.techservice.shared.dtos.ResponseAddressDTO;

import java.time.Instant;
import java.util.UUID;

public record ClientResponseDTO(
        UUID id,
        String name,
        String phone,
        String email,
        String documentType,
        String document,
        ResponseAddressDTO address,
        Boolean isActive,
        Instant createdAt,
        Instant updatedAt
) {
}
