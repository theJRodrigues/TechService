package com.techservice.techservice.modules.client.dto;

import java.util.UUID;

public record SearchSuggestionsByNameResponseDTO(
        UUID id,
        String name
) {
}
