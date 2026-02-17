package com.techservice.techservice.modules.client.infra.persistence;

import com.techservice.techservice.modules.client.domain.Client;
import com.techservice.techservice.modules.client.domain.ClientRepository;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface JpaClientRepository extends JpaRepository<Client, UUID>, ClientRepository {
}
