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
        ResponseCookie cookie = ResponseCookie.from("access_token", null)
                .secure(true)
                .httpOnly(true)
                .sameSite("Strict")
                .path("/")
                .build();
        return ResponseEntity.noContent().header(HttpHeaders.SET_COOKIE, cookie.toString()).build();
    }
}
