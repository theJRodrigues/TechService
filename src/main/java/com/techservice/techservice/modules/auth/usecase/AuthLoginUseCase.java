package com.techservice.techservice.modules.auth.usecase;

import com.techservice.techservice.modules.account.domain.Account;
import com.techservice.techservice.modules.auth.domain.AuthRepository;
import com.techservice.techservice.modules.auth.domain.RefreshToken;
import com.techservice.techservice.modules.auth.domain.RefreshTokenRepository;
import com.techservice.techservice.modules.auth.dto.AuthLoginRequestDTO;
import com.techservice.techservice.modules.auth.dto.AuthLoginResponseDTO;
import com.techservice.techservice.modules.auth.services.RefreshTokenService;
import com.techservice.techservice.shared.exceptions.InvalidCredentialsException;
import com.techservice.techservice.shared.services.JWTService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthLoginUseCase {
    private final AuthRepository repository;
    private final PasswordEncoder passwordEncoder;
    private final JWTService jwtService;
    private final RefreshTokenRepository rtRepository;
    private final RefreshTokenService rtService;

    public AuthLoginUseCase(AuthRepository repository, PasswordEncoder encoder, JWTService jwt, RefreshTokenRepository rtRepository, RefreshTokenService rtService){
        this.repository = repository;
        this.passwordEncoder = encoder;
        this.jwtService = jwt;
        this.rtRepository = rtRepository;
        this.rtService = rtService;
    }

    public AuthLoginResponseDTO execute(AuthLoginRequestDTO dto){
        Account account = repository
                .findByEmail(dto.email())
                .orElseThrow(InvalidCredentialsException::new);

        if(!passwordEncoder.matches(dto.password(), account.getPassword())){
            throw new InvalidCredentialsException();
        };

        String jwtToken = jwtService.generateToken(account.getId(), account.getCompany().getId(), account.getRole());
        String rawRT = rtService.generateRawToken();
        String hashRT = rtService.hash(rawRT);

        RefreshToken refreshToken = RefreshToken.create(
                account.getId(),
                account.getCompany().getId(),
                account.getRole(),
                hashRT
        );

        RefreshToken newRT = rtRepository.save(refreshToken);


        return new AuthLoginResponseDTO(jwtToken, rawRT);
    }
}
