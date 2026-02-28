package com.techservice.techservice.modules.client.controller;

import com.techservice.techservice.modules.client.dto.ClientResponseDTO;
import com.techservice.techservice.modules.client.dto.CreateClientRequestDTO;
import com.techservice.techservice.modules.client.dto.UpdateClientRequestDTO;
import com.techservice.techservice.modules.client.usecase.CreateClientUseCase;
import com.techservice.techservice.modules.client.usecase.UpdateClientUseCase;
import com.techservice.techservice.shared.Routes.Routes;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping(Routes.CLIENT)
public class ClientController {
    private final CreateClientUseCase createClientUseCase;
    private final UpdateClientUseCase updateClientUseCase;

    public ClientController(CreateClientUseCase useCase, UpdateClientUseCase updateClientUseCase) {
        this.createClientUseCase = useCase;
        this.updateClientUseCase = updateClientUseCase;
    }

    @PostMapping
    public ResponseEntity<ClientResponseDTO> create(@RequestBody @Valid CreateClientRequestDTO dto){
        ClientResponseDTO newClient = createClientUseCase.execute(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(newClient);
    }

    @PatchMapping("/update/{id}")
    public ResponseEntity<ClientResponseDTO> update(@Valid @RequestBody UpdateClientRequestDTO dto, @PathVariable UUID id){
        ClientResponseDTO updatedClient = updateClientUseCase.execute(dto, id);
        return ResponseEntity.status(HttpStatus.OK).body(updatedClient);

    }
}
