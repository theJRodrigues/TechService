package com.techservice.techservice.modules.client.usecase;

import com.techservice.techservice.config.security.AuthenticatedAccount;
import com.techservice.techservice.modules.client.domain.Client;
import com.techservice.techservice.modules.client.domain.ClientRepository;
import com.techservice.techservice.modules.client.dto.ClientResponseDTO;
import com.techservice.techservice.modules.client.dto.UpdateClientRequestDTO;
import com.techservice.techservice.modules.client.mapper.ClientMapper;
import com.techservice.techservice.modules.company.domain.CompanyRepository;
import com.techservice.techservice.shared.exceptions.BusinessRuleException;
import com.techservice.techservice.shared.exceptions.NotFoundException;
import com.techservice.techservice.shared.services.AuthenticatedAccountProvider;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
public class UpdateClientUseCase {
    private final ClientRepository repository;
    private final AuthenticatedAccountProvider authProvider;
    private final CompanyRepository companyRepository;

    public UpdateClientUseCase(ClientRepository repository,
                               AuthenticatedAccountProvider authProvider,
                               CompanyRepository companyRepository) {
        this.repository = repository;
        this.authProvider = authProvider;
        this.companyRepository = companyRepository;
    }

    @Transactional
    public ClientResponseDTO execute(UpdateClientRequestDTO dto, UUID id){
        if (dto.name() == null &&
                dto.phone() == null &&
                dto.email() == null &&
                dto.address() == null) {
            throw new BusinessRuleException("At least one field must be provided");
        }
        AuthenticatedAccount auth = authProvider.get();

        Client client = repository.findByIdAndCompanyId(id, auth.companyId())
                .orElseThrow(NotFoundException::new);


        if(dto.address() != null){
            client.updateAddress(dto.address().zipCode(),
                    dto.address().street(),
                    dto.address().number(),
                    dto.address().neighborhood(),
                    dto.address().city(),
                    dto.address().complement());

        }

        client.update(dto.name(), dto.phone(), dto.name());

        return ClientMapper.toResponse(client);
    }
}
