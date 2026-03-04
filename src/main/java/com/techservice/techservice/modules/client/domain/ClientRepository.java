package com.techservice.techservice.modules.client.domain;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;
import java.util.UUID;

public interface ClientRepository {
    Client save(Client client);

    Optional<Client> findByIdAndCompanyId(UUID id, UUID companyId);

    Page<Client> findAllByCompanyId(UUID companyId, Pageable pageable);

}
