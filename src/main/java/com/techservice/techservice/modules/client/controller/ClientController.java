package com.techservice.techservice.modules.client.controller;

import com.techservice.techservice.modules.client.dto.ClientResponseDTO;
import com.techservice.techservice.modules.client.dto.CreateClientRequestDTO;
import com.techservice.techservice.modules.client.dto.UpdateClientRequestDTO;
import com.techservice.techservice.modules.client.usecase.CreateClientUseCase;
import com.techservice.techservice.modules.client.usecase.GetClientsPageUseCase;
import com.techservice.techservice.modules.client.usecase.UpdateClientUseCase;
import com.techservice.techservice.shared.Routes.Routes;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping(Routes.CLIENT)
public class ClientController {
    private final CreateClientUseCase createClientUseCase;
    private final UpdateClientUseCase updateClientUseCase;
    private final GetClientsPageUseCase getClientsPageUseCase;

    public ClientController(CreateClientUseCase useCase, UpdateClientUseCase updateClientUseCase, GetClientsPageUseCase getClientsPageUseCase) {
        this.createClientUseCase = useCase;
        this.updateClientUseCase = updateClientUseCase;
        this.getClientsPageUseCase = getClientsPageUseCase;
    }

    @PostMapping
    public ResponseEntity<ClientResponseDTO> create(@RequestBody @Valid CreateClientRequestDTO dto){
        ClientResponseDTO newClient = createClientUseCase.execute(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(newClient);
    }

    @PatchMapping("/{id}/update")
    public ResponseEntity<ClientResponseDTO> update(@Valid @RequestBody UpdateClientRequestDTO dto, @PathVariable UUID id){
        ClientResponseDTO updatedClient = updateClientUseCase.execute(dto, id);
        return ResponseEntity.status(HttpStatus.OK).body(updatedClient);
    }

    @GetMapping()
    public ResponseEntity<Page<ClientResponseDTO>> getPage(@RequestParam(name = "page", defaultValue = "0") int page,
                                                @RequestParam(name = "size", defaultValue = "20") int size,
                                                @RequestParam(name = "sort", defaultValue = "name") String sortBy,
                                                @RequestParam(name = "direction", defaultValue = "asc") String direction){
        Sort sort = direction.equalsIgnoreCase("asc")
                ? Sort.by(sortBy).ascending()
                : Sort.by(sortBy).descending();

        Pageable pageable = PageRequest.of(page,size,sort);

        Page<ClientResponseDTO> clients = getClientsPageUseCase.execute(pageable);

        return ResponseEntity.ok(clients);
    }
}
