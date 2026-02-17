package com.techservice.techservice.modules.client.dto;

import com.techservice.techservice.shared.dtos.AddressDTO;
import com.techservice.techservice.shared.enums.DocumentType;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record CreateClientRequestDTO(
        @NotBlank(message = "Name must not be empty")
        @Size(min = 3, max = 150, message = "Name must be between 3 and 150 characters")
        String name,

        @NotBlank(message = "Phone must not be empty")
        String phone,

        @NotBlank(message = "Email must not be empty")
        @Email(message = "Invalid email format")
        String email,

        @NotNull(message = "Document type must not be empty")
        DocumentType documentType,

        @NotBlank(message = "Document must not be empty")
        String document,

        @NotNull(message = "Address must not be empty")
        @Valid
        AddressDTO address
) {
}
