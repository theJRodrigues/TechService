package com.techservice.techservice.modules.auth.controller;

import com.techservice.techservice.modules.auth.usecase.AuthLogoutUseCase;
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
@RequestMapping(Routes.AUTH + "/logout")
public class AuthLogoutController {
     private final AuthLogoutUseCase useCase;

    public AuthLogoutController(AuthLogoutUseCase useCase) {
        this.useCase = useCase;
    }

    @PostMapping
    public ResponseEntity<Void> execute(@CookieValue(name = "refresh_token",
            required = false) String refreshToken){
        if(refreshToken != null) useCase.execute(refreshToken);


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
}
