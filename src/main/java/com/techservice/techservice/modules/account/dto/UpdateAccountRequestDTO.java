package com.techservice.techservice.modules.account.dto;

import jakarta.annotation.Nullable;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;

public record UpdateAccountRequestDTO(
        @Nullable
        @Size(min = 3, max = 150, message = "The name must contain a minimum of 3 and a maximum of 150 characters.")
        String name,

        @Nullable
        @Email(message = "Invalid email format")
        String email
) { }
