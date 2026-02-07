package com.techservice.techservice.modules.account.controller;

import com.techservice.techservice.modules.account.usecase.DeactivateAccountByIdUseCase;
import com.techservice.techservice.shared.Routes.Routes;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping(Routes.ACCOUNT)
public class DeactivateAccountByIdController {

    private final DeactivateAccountByIdUseCase useCase;

    public DeactivateAccountByIdController(DeactivateAccountByIdUseCase useCase) {
        this.useCase = useCase;
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PatchMapping("/{id}")
    public ResponseEntity<Void> execute(@PathVariable UUID id){
        useCase.execute(id);
        return ResponseEntity.noContent().build();
    }
}
