package com.techservice.techservice.modules.company.dto;

import java.util.UUID;

public record CompanyResponseRequest(
        UUID id,
        String name
) {
}
