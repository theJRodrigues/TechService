package com.techservice.techservice.modules.client.usecase;

import com.techservice.techservice.config.security.AuthenticatedAccount;
import com.techservice.techservice.modules.client.domain.Client;
import com.techservice.techservice.modules.client.domain.ClientRepository;
import com.techservice.techservice.modules.client.dto.SearchSuggestionsByNameResponseDTO;
import com.techservice.techservice.modules.client.mapper.ClientMapper;
import com.techservice.techservice.shared.services.AuthenticatedAccountProvider;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FindClientSuggestionsByNameUseCase {
    private final ClientRepository repository;
    private final AuthenticatedAccountProvider authProvider;

    public FindClientSuggestionsByNameUseCase(ClientRepository repository, AuthenticatedAccountProvider authProvider) {
        this.repository = repository;
        this.authProvider = authProvider;
    }

    public List<SearchSuggestionsByNameResponseDTO> execute(String name){
        if (name == null || name.isBlank() || name.length() <=2){
            return List.of();
        }

        AuthenticatedAccount auth = authProvider.get();
        List<Client> foundedClients = repository
                .findTop5ByCompanyIdAndNameStartingWithIgnoreCase(auth.companyId(), name);

        return foundedClients.stream().map(ClientMapper::toSearchSuggestionsResponse).toList();
    }
}
