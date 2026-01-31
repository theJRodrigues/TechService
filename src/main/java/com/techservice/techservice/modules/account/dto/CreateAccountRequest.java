package com.techservice.techservice.modules.account.dto;

import com.techservice.techservice.shared.enums.Role;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record CreateAccountRequest(
        @NotBlank(message = "Name must not be empty")
        @Size(min = 3, max = 150, message = "The name must contain a minimum of 3 and a maximum of 150 characters.")
        String name,

        @NotBlank(message = "Email must not be empty")
        @Email(message = "Format invalid")
        String email,

        @NotBlank(message = "Password must not be empty")
        @Size(min = 8, max = 16, message = "The password must contain a minimum of 8 and a maximum of 16 characters.")
        String password,

        @NotNull(message = "Role must not be empty")
        Role role
) {
}
