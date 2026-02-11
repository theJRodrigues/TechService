package com.techservice.techservice.modules.auth.dto;

public record AuthLoginResponseDTO(
        String accessToken,
        String refreshToken
) {
}
