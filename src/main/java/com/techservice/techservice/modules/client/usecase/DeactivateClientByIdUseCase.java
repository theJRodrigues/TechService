package com.techservice.techservice.modules.client.usecase;

import com.techservice.techservice.config.security.AuthenticatedAccount;
import com.techservice.techservice.modules.client.domain.Client;
import com.techservice.techservice.modules.client.domain.ClientRepository;
import com.techservice.techservice.shared.exceptions.NotFoundException;
import com.techservice.techservice.shared.services.AuthenticatedAccountProvider;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class DeactivateClientByIdUseCase {
    private final ClientRepository repository;
    private final AuthenticatedAccountProvider authProvider;

    public DeactivateClientByIdUseCase(ClientRepository repository, AuthenticatedAccountProvider authProvider) {
        this.repository = repository;
        this.authProvider = authProvider;
    }

    public void execute(UUID id){
        AuthenticatedAccount auth = authProvider.get();

        Client client = repository.findByIdAndCompanyId(id, auth.companyId())
                .orElseThrow(() -> new NotFoundException("The Client could not be found"));

        client.deactivate();
    }
}
