package com.techservice.techservice.shared.services;

import java.util.UUID;

public record TokenPayload(UUID accountId, String role) {
}
