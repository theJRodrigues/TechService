package com.techservice.techservice.modules.client.domain;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ClientRepository {
    Client save(Client client);

    Optional<Client> findByIdAndCompanyId(UUID id, UUID companyId);

    Optional<Client> findByIdAndCompanyIdAndIsActiveTrue(UUID id, UUID companyId);

    List<Client> findAllByCompanyIdAndIsActiveTrue(UUID companyId);

    List<Client> findAllByCompanyId(UUID companyId);
}
