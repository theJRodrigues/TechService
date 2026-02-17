package com.techservice.techservice.modules.client.usecase;

import com.techservice.techservice.config.security.AuthenticatedAccount;
import com.techservice.techservice.modules.client.dto.ClientResponseDTO;
import com.techservice.techservice.modules.client.dto.CreateClientRequestDTO;
import com.techservice.techservice.modules.client.domain.Client;
import com.techservice.techservice.modules.client.domain.ClientRepository;
import com.techservice.techservice.modules.client.mapper.ClientMapper;
import com.techservice.techservice.modules.company.domain.Company;
import com.techservice.techservice.modules.company.domain.CompanyRepository;
import com.techservice.techservice.shared.exceptions.InvalidTokenContextException;
import com.techservice.techservice.shared.services.AuthenticatedAccountProvider;
import org.springframework.stereotype.Service;

@Service
public class CreateClientUseCase {
    private final ClientRepository repository;
    private final CompanyRepository companyRepository;
    private final AuthenticatedAccountProvider authProvider;

    public CreateClientUseCase(ClientRepository repository, CompanyRepository companyRepository, AuthenticatedAccountProvider authProvider) {
        this.repository = repository;
        this.companyRepository = companyRepository;
        this.authProvider = authProvider;
    }

    public ClientResponseDTO execute(CreateClientRequestDTO dto){
        AuthenticatedAccount auth = authProvider.get();
        Company company = companyRepository.findById(auth.companyId())
                .orElseThrow(InvalidTokenContextException::new);

        Client client = ClientMapper.fromCreateToEntity(dto,company);

        Client newClient = repository.save(client);

        return ClientMapper.toResponse(newClient);
    }
}
