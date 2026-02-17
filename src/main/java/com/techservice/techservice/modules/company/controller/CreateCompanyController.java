package com.techservice.techservice.modules.company.controller;

import com.techservice.techservice.modules.company.dto.CreateCompanyRequest;
import com.techservice.techservice.modules.company.usecase.CreateCompanyUseCase;
import com.techservice.techservice.shared.Routes.Routes;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(Routes.COMPANY)
public class CreateCompanyController {
    private final CreateCompanyUseCase useCase;

    public CreateCompanyController(CreateCompanyUseCase useCase) {
        this.useCase = useCase;
    }

    @PostMapping
    public ResponseEntity<Void> execute(@Valid @RequestBody CreateCompanyRequest dto) {
        useCase.execute(dto);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
}
