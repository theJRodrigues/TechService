package com.techservice.techservice.modules.auth.controller;

import com.techservice.techservice.modules.auth.dto.AuthLoginResponseDTO;
import com.techservice.techservice.modules.auth.usecase.AuthRefreshTokenUseCase;
import com.techservice.techservice.shared.Routes.Routes;
import com.techservice.techservice.shared.exceptions.InvalidRefreshTokenException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping(Routes.AUTH + "/refresh")
public class AuthRefreshTokenController {
    private final AuthRefreshTokenUseCase useCase;

    public AuthRefreshTokenController(AuthRefreshTokenUseCase useCase) {
        this.useCase = useCase;
    }

    @PostMapping()
    public ResponseEntity<Void> execute(@CookieValue(name = "refresh_token", required = false)
                                        String refreshToken){

        if(refreshToken == null) throw new InvalidRefreshTokenException();
        AuthLoginResponseDTO tokens = useCase.execute(refreshToken);

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
