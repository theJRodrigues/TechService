package com.techservice.techservice.modules.auth.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record AuthLoginRequest(
        @NotBlank(message = "Email must not be empty")
        @Email(message = "Format invalid")
        String email,

        @NotBlank(message = "Password must not be empty")
        @Size(min = 8, max = 16, message = "The password must contain a minimum of 8 and a maximum of 16 characters.")
        String password
) { }
