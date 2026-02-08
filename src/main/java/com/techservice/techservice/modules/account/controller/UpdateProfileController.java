package com.techservice.techservice.modules.account.controller;


import com.techservice.techservice.modules.account.dto.AccountResponseDTO;
import com.techservice.techservice.modules.account.dto.UpdateProfileRequestDTO;
import com.techservice.techservice.modules.account.usecase.UpdateProfileUseCase;
import com.techservice.techservice.shared.Routes.Routes;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping(Routes.ACCOUNT)
public class UpdateProfileController {
    private final UpdateProfileUseCase useCase;

    public UpdateProfileController(UpdateProfileUseCase useCase) {
        this.useCase = useCase;
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PatchMapping("/{id}/update")
    public ResponseEntity<?> execute(@PathVariable UUID id, @RequestBody @Valid UpdateProfileRequestDTO dto) {
        AccountResponseDTO updatedAccount = useCase.execute(id, dto);
        return ResponseEntity.ok(updatedAccount);
    }
}
