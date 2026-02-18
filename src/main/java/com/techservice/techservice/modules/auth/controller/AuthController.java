package com.techservice.techservice.modules.auth.controller;

import com.techservice.techservice.modules.auth.dto.AuthLoginRequestDTO;
import com.techservice.techservice.modules.auth.dto.AuthLoginResponseDTO;
import com.techservice.techservice.modules.auth.usecase.AuthLoginUseCase;
import com.techservice.techservice.modules.auth.usecase.AuthLogoutUseCase;
import com.techservice.techservice.modules.auth.usecase.AuthRefreshTokenUseCase;
import com.techservice.techservice.shared.Routes.Routes;
import com.techservice.techservice.shared.exceptions.InvalidRefreshTokenException;
import jakarta.validation.Valid;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(Routes.AUTH)
public class AuthController {
    private final AuthLoginUseCase loginUseCase;
    private final AuthLogoutUseCase logoutUseCase;
    private final AuthRefreshTokenUseCase refreshTokenUseCase;

    public AuthController(AuthLoginUseCase loginUseCase,
                          AuthLogoutUseCase logoutUseCase,
                          AuthRefreshTokenUseCase refreshTokenUseCase) {
        this.loginUseCase = loginUseCase;
        this.logoutUseCase = logoutUseCase;
        this.refreshTokenUseCase = refreshTokenUseCase;
    }

    @PostMapping("/login")
    public ResponseEntity<Void> login(@Valid @RequestBody AuthLoginRequestDTO dto) {
        AuthLoginResponseDTO token = loginUseCase.execute(dto);
        ResponseCookie jwtCookie = ResponseCookie.from("access_token", token.accessToken())
                .httpOnly(true)
                .secure(true)
                .sameSite("Strict")
                .path("/")
                .build();
        ResponseCookie refreshCookie = ResponseCookie.from("refresh_token", token.refreshToken())
                .httpOnly(true)
                .secure(true)
                .sameSite("Strict")
                .path(Routes.AUTH)
                .build();

        return ResponseEntity.ok()
                .header(HttpHeaders.SET_COOKIE, jwtCookie.toString())
                .header(HttpHeaders.SET_COOKIE, refreshCookie.toString())
                .build();
    }

    @PostMapping("/logout")
    public ResponseEntity<Void> logout(@CookieValue(name = "refresh_token", required = false)
                                       String refreshToken) {
        if (refreshToken != null) logoutUseCase.execute(refreshToken);

        ResponseCookie jwtCookie = ResponseCookie.from("access_token", "")
                .secure(true)
                .httpOnly(true)
                .sameSite("Strict")
                .path("/")
                .maxAge(0)
                .build();

        ResponseCookie refreshCookie = ResponseCookie.from("refresh_token", "")
                .secure(true)
                .httpOnly(true)
                .sameSite("Strict")
                .path(Routes.AUTH)
                .maxAge(0)
                .build();

        return ResponseEntity.noContent()
                .header(HttpHeaders.SET_COOKIE, jwtCookie.toString())
                .header(HttpHeaders.SET_COOKIE, refreshCookie.toString())
                .build();
    }

    @PostMapping("/refresh")
    public ResponseEntity<Void> refresh(@CookieValue(name = "refresh_token", required = false)
                                        String refreshToken) {

        if (refreshToken == null) throw new InvalidRefreshTokenException();
        AuthLoginResponseDTO tokens = refreshTokenUseCase.execute(refreshToken);

        ResponseCookie jwtCookie = ResponseCookie.from("access_token", tokens.accessToken())
                .httpOnly(true)
                .secure(true)
                .sameSite("Strict")
                .path("/")
                .build();
        ResponseCookie refreshCookie = ResponseCookie.from("refresh_token", tokens.refreshToken())
                .httpOnly(true)
                .secure(true)
                .sameSite("Strict")
                .path(Routes.AUTH)
                .build();

        return ResponseEntity.ok()
                .header(HttpHeaders.SET_COOKIE, jwtCookie.toString())
                .header(HttpHeaders.SET_COOKIE, refreshCookie.toString())
                .build();
    }
}
