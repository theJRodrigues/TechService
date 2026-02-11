package com.techservice.techservice.modules.auth.controller;

import com.techservice.techservice.shared.Routes.Routes;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(Routes.AUTH + "/logoff")
public class AuthLogoffController {

    @PostMapping
    public ResponseEntity<Void> execute(){
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
                .path(Routes.AUTH + "/refresh")
                .maxAge(0)
                .build();

        return ResponseEntity.noContent()
                .header(HttpHeaders.SET_COOKIE, jwtCookie.toString())
                .header(HttpHeaders.SET_COOKIE, refreshCookie.toString())
                .build();
    }
}
