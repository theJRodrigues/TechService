package com.techservice.techservice.modules.client.mapper;

import com.techservice.techservice.modules.client.domain.Client;
import com.techservice.techservice.modules.client.dto.ClientResponseDTO;
import com.techservice.techservice.modules.client.dto.CreateClientRequestDTO;
import com.techservice.techservice.modules.company.domain.Company;
import com.techservice.techservice.shared.valueObjects.Address;

public class ClientMapper {
    public static Client fromCreateToEntity(CreateClientRequestDTO dto, Company company){
        String normalizedPhone = dto.phone().replaceAll("\\D", "");

        Address address = Address.create(dto.address().zipCode(),
                dto.address().street(),
                dto.address().number(),
                dto.address().neighborhood(),
                dto.address().city(),
                dto.address().complement());



        return Client.create(dto.name(),
                normalizedPhone,
                dto.email(),
                dto.documentType(),
                dto.document(),
                address,
                company);
    }

    public static ClientResponseDTO toResponse(Client client){
        return new ClientResponseDTO(client.getId(),
                client.getName(),
                client.getPhone(),
                client.getEmail(),
                client.getDocumentType().name(),
                client.getDocument(),
                client.getAddress(),
                client.isActive(),
                client.getCreatedAt(),
                client.getUpdatedAt());
    }
}
