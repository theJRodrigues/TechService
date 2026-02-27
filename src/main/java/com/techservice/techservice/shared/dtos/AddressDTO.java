package com.techservice.techservice.shared.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record AddressDTO(
        @NotBlank(message = "CEP must not be empty")
        String zipCode,

        @NotBlank(message = "Street must not be empty")
        String street,

        @NotBlank(message = "Number must not be empty")
        String number,

        @Size(max = 80, message = "Neighborhood must be at most 80 characters")
        String neighborhood,

        @Size(max = 80, message = "City must be at most 80 characters")
        String city,

        @Size(max = 100, message = "Complement must be at most 100 characters")
        String complement
) {
}
