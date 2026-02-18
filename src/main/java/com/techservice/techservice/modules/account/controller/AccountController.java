package com.techservice.techservice.modules.account.controller;

import com.techservice.techservice.modules.account.dto.AccountResponseDTO;
import com.techservice.techservice.modules.account.dto.CreateAccountRequestDTO;
import com.techservice.techservice.modules.account.dto.UpdateProfileRequestDTO;
import com.techservice.techservice.modules.account.usecase.CreateAccountUseCase;
import com.techservice.techservice.modules.account.usecase.DeactivateAccountByIdUseCase;
import com.techservice.techservice.modules.account.usecase.GetAllAccountsUseCase;
import com.techservice.techservice.modules.account.usecase.UpdateProfileUseCase;
import com.techservice.techservice.shared.Routes.Routes;
import jakarta.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping(Routes.ACCOUNT)
public class AccountController {
    private final CreateAccountUseCase createUseCase;
    private final DeactivateAccountByIdUseCase deactivateUseCase;
    private final GetAllAccountsUseCase getAllUseCase;
    private final UpdateProfileUseCase updateUseCase;


    public AccountController(CreateAccountUseCase createUseCase,
                             DeactivateAccountByIdUseCase deactivateUseCase,
                             GetAllAccountsUseCase getAllUseCase,
                             UpdateProfileUseCase updateUseCase) {
        this.createUseCase = createUseCase;
        this.deactivateUseCase = deactivateUseCase;
        this.getAllUseCase = getAllUseCase;
        this.updateUseCase = updateUseCase;
    }

    @GetMapping
    public ResponseEntity<List<AccountResponseDTO>> getAll(){
        List<AccountResponseDTO> accounts = getAllUseCase.execute();
        if(accounts.isEmpty()){
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(accounts);
        }
        return ResponseEntity.status(HttpStatus.OK).body(accounts);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public ResponseEntity<AccountResponseDTO> create(@Valid @RequestBody CreateAccountRequestDTO dto) {
        AccountResponseDTO response = createUseCase.execute(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }


    @PreAuthorize("hasRole('ADMIN')")
    @PatchMapping("/{id}/update")
    public ResponseEntity<?> update(@PathVariable UUID id, @RequestBody @Valid UpdateProfileRequestDTO dto) {
        AccountResponseDTO updatedAccount = updateUseCase.execute(id, dto);
        return ResponseEntity.ok(updatedAccount);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PatchMapping("/{id}/deactivate")
    public ResponseEntity<Void> deactivate(@PathVariable UUID id){
        deactivateUseCase.execute(id);
        return ResponseEntity.noContent().build();
    }


}


