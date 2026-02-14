package com.techservice.techservice.modules.auth.usecase;

import com.techservice.techservice.modules.auth.domain.RefreshTokenRepository;
import com.techservice.techservice.modules.auth.services.RefreshTokenService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AuthLogoutUseCase {
    private final RefreshTokenRepository repository;
    private final RefreshTokenService service;

    public AuthLogoutUseCase(RefreshTokenRepository repository, RefreshTokenService service) {
        this.repository = repository;
        this.service = service;
    }

    @Transactional
    public void execute(String refreshToken){
        String tokenHash = service.hash(refreshToken);
        int updated = repository.revokeByTokenHash(tokenHash);
    }
}
