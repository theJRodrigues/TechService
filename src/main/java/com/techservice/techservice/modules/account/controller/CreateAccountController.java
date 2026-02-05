package com.techservice.techservice.modules.account.controller;

import com.techservice.techservice.modules.account.dto.AccountResponseRequest;
import com.techservice.techservice.modules.account.dto.CreateAccountRequest;
import com.techservice.techservice.modules.account.usecase.CreateAccountUseCase;
import com.techservice.techservice.shared.Routes.Routes;
import jakarta.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(Routes.ACCOUNT)
public class CreateAccountController {
    private final CreateAccountUseCase useCase;

    public CreateAccountController(CreateAccountUseCase useCase) {
        this.useCase = useCase;
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public ResponseEntity<AccountResponseRequest> execute(@Valid @RequestBody CreateAccountRequest dto) {
        AccountResponseRequest response = useCase.execute(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
}


