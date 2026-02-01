package com.techservice.techservice.modules.auth.usecase;

import com.techservice.techservice.modules.account.domain.Account;
import com.techservice.techservice.modules.auth.domain.AuthLoginRepository;
import com.techservice.techservice.modules.auth.dto.AuthLoginRequest;
import com.techservice.techservice.modules.auth.dto.AuthLoginRequestResponse;
import com.techservice.techservice.shared.exceptions.InvalidCredentialsException;
import com.techservice.techservice.shared.services.JWTService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthLoginUseCase {
    private final AuthLoginRepository repository;
    private final PasswordEncoder passwordEncoder;
    private final JWTService jwtService;

    public AuthLoginUseCase(AuthLoginRepository repository, PasswordEncoder encoder, JWTService jwt){
        this.repository = repository;
        this.passwordEncoder = encoder;
        jwtService = jwt;
    }

    public AuthLoginRequestResponse execute(AuthLoginRequest dto){
        Account account = repository
                .findByEmail(dto.email())
                .orElseThrow(InvalidCredentialsException::new);

        if(!passwordEncoder.matches(dto.password(), account.getPassword())){
            throw new InvalidCredentialsException();
        };

        String token = jwtService.generateToken(account.getId().toString(), account.getRole().name());

        return new AuthLoginRequestResponse(token);
    }
}
