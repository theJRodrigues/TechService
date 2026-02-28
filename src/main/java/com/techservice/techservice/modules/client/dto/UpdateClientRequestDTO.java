package com.techservice.techservice.modules.client.dto;

import com.techservice.techservice.shared.dtos.AddressDTO;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;

public record UpdateClientRequestDTO(
        @Size(min = 3, max = 150, message = "Name must be between 3 and 150 characters")
        String name,

        @Size(min = 11, max = 11, message = "Phone must be  characters")
        String phone,

        @Email(message = "Invalid email format")
        String email,

        @Valid
        AddressDTO address
) {
}
