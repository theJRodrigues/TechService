package com.techservice.techservice.modules.company.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record CreateCompanyRequest(
        @NotBlank(message = "Company name must not be empty")
        @Size(min = 3, max = 150, message = "The company name must contain a minimum of 3 and maximum of 150 characters")
        String companyName,

        @NotBlank(message = "CNPJ company must not be empty")
        @Size(min = 14, max = 14, message = "CNPJ Format invalid")
        String CNPJ,

        @NotBlank(message = "Admin name must not be empty")
        @Size(min = 3, max = 150, message = "The account name must contain a minimum of 3 and maximum of 150 characters")
        String adminName,

        @NotBlank(message = "Email must not be empty")
        @Email(message = " Email format Invalid")
        String adminEmail,

        @NotBlank(message = "Password must not be empty")
        @Size(min = 8, max = 25, message = "The password must contain a minimum of 8 and maximum of 25 characters")
        String adminPassword
) {
}
