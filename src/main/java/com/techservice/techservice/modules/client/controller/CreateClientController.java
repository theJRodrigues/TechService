package com.techservice.techservice.modules.client.controller;

import com.techservice.techservice.modules.client.dto.ClientResponseDTO;
import com.techservice.techservice.modules.client.dto.CreateClientRequestDTO;
import com.techservice.techservice.modules.client.usecase.CreateClientUseCase;
import com.techservice.techservice.shared.Routes.Routes;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(Routes.CLIENT)
public class CreateClientController {
    private final CreateClientUseCase useCase;

    public CreateClientController(CreateClientUseCase useCase) {
        this.useCase = useCase;
    }

    @PostMapping
    public ResponseEntity<ClientResponseDTO> execute(@RequestBody @Valid CreateClientRequestDTO dto){
        ClientResponseDTO newClient = useCase.execute(dto);

        return ResponseEntity.status(HttpStatus.CREATED).body(newClient);
    }
}
