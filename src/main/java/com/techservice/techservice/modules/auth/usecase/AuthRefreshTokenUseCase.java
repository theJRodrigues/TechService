package com.techservice.techservice.modules.auth.usecase;

import com.techservice.techservice.modules.account.domain.AccountRepository;
import com.techservice.techservice.modules.auth.domain.RefreshToken;
import com.techservice.techservice.modules.auth.domain.RefreshTokenRepository;
import com.techservice.techservice.modules.auth.dto.AuthLoginResponseDTO;
import com.techservice.techservice.modules.auth.services.RefreshTokenRevocationService;
import com.techservice.techservice.modules.auth.services.RefreshTokenService;
import com.techservice.techservice.shared.exceptions.InvalidRefreshTokenException;
import com.techservice.techservice.shared.services.JWTService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;

@Service
public class AuthRefreshTokenUseCase {
    private final RefreshTokenRepository repository;
    private final RefreshTokenService rtService;
    private final JWTService jwtService;
    private final AccountRepository accRepository;
    private final RefreshTokenRevocationService revokeService;


    public AuthRefreshTokenUseCase(RefreshTokenRepository repository,
                                   RefreshTokenService rtService,
                                   JWTService jwtService,
                                   AccountRepository accRepository, RefreshTokenRevocationService revokeService) {
        this.repository = repository;
        this.rtService = rtService;
        this.jwtService = jwtService;
        this.accRepository = accRepository;

        this.revokeService = revokeService;
    }

    @Transactional
    public AuthLoginResponseDTO execute(String refreshToken) {
        String hashRTReceived = rtService.hash(refreshToken);
        RefreshToken foundRT = repository.findByTokenHash(hashRTReceived)
                .orElseThrow(InvalidRefreshTokenException::new);

        int revokedToken = repository.revokeIfValid(hashRTReceived, Instant.now());

        if (revokedToken == 0) {
            revokeService.revokeAllByAccountId(foundRT.getAccountId());
            throw new InvalidRefreshTokenException();
        };

        if (!accRepository.existsByIdAndCompanyIdAndIsActiveTrue(foundRT.getAccountId(), foundRT.getCompanyId())) {
            throw new InvalidRefreshTokenException();
        }

        String rawRT = rtService.generateRawToken();
        String hashRT = rtService.hash(rawRT);
        RefreshToken newRT = RefreshToken.create(
                foundRT.getAccountId(),
                foundRT.getCompanyId(),
                foundRT.getRole(),
                hashRT
        );

        RefreshToken savedRT = repository.save(newRT);


        String jwt = jwtService.generateToken(savedRT.getAccountId(), savedRT.getCompanyId(), savedRT.getRole());

        return new AuthLoginResponseDTO(jwt, rawRT);
    }
}
