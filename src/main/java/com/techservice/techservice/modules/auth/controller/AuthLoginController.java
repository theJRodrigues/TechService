package com.techservice.techservice.modules.auth.controller;

import com.techservice.techservice.modules.auth.dto.AuthLoginRequest;
import com.techservice.techservice.modules.auth.dto.AuthLoginRequestResponse;
import com.techservice.techservice.modules.auth.usecase.AuthLoginUseCase;
import jakarta.validation.Valid;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/login")
public class AuthLoginController {
    private final AuthLoginUseCase useCase;

    public AuthLoginController(AuthLoginUseCase useCase) {
        this.useCase = useCase;
    }

    @PostMapping
    public ResponseEntity<Void> execute(@Valid @RequestBody AuthLoginRequest dto){
        AuthLoginRequestResponse token = useCase.execute(dto);
        ResponseCookie cookie = ResponseCookie.from("access_token", token.accessToken())
                .httpOnly(true)
                .secure(true)
                .sameSite("Strict")
                .path("/")
                .build();

        return ResponseEntity.ok().header(HttpHeaders.SET_COOKIE, cookie.toString())
                .build();
    }

}
