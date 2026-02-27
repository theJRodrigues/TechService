package com.techservice.techservice.shared.dtos;

public record ResponseAddressDTO(
        String zipCode,
        String street,
        String number,
        String neighborhood,
        String city,
        String complement
) {
}
