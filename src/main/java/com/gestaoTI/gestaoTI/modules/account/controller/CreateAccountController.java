package com.gestaoTI.gestaoTI.modules.account.controller;

import com.gestaoTI.gestaoTI.modules.account.dto.AccountResponse;
import com.gestaoTI.gestaoTI.modules.account.dto.CreateAccountRequest;
import com.gestaoTI.gestaoTI.modules.account.usecase.CreateAccountUseCase;
import jakarta.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/account/create")
public class CreateAccountController {
    private final CreateAccountUseCase useCase;

    public CreateAccountController(CreateAccountUseCase useCase) {
        this.useCase = useCase;
    }

    @PostMapping
    public ResponseEntity<AccountResponse> execute(@Valid @RequestBody CreateAccountRequest dto){
        AccountResponse response = useCase.execute(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
}


