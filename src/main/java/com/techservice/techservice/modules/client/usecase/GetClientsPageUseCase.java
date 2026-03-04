package com.techservice.techservice.modules.client.usecase;

import com.techservice.techservice.config.security.AuthenticatedAccount;
import com.techservice.techservice.modules.client.domain.Client;
import com.techservice.techservice.modules.client.domain.ClientRepository;
import com.techservice.techservice.modules.client.dto.ClientResponseDTO;
import com.techservice.techservice.modules.client.mapper.ClientMapper;
import com.techservice.techservice.shared.services.AuthenticatedAccountProvider;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class GetClientsPageUseCase {
    private final ClientRepository repository;
    private final AuthenticatedAccountProvider authProvider;

    public GetClientsPageUseCase(ClientRepository repository, AuthenticatedAccountProvider authProvider) {
        this.repository = repository;
        this.authProvider = authProvider;
    }

    public Page<ClientResponseDTO> execute(Pageable pageable){
        AuthenticatedAccount auth = authProvider.get();

        Page<Client> clientsPage = repository.findAllByCompanyId(auth.companyId(), pageable);

        return clientsPage.map(ClientMapper::toResponse);
    }
}
