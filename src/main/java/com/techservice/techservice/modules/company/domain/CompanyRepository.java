package com.techservice.techservice.modules.company.domain;


import java.util.Optional;
import java.util.UUID;

public interface CompanyRepository {
    Company save(Company company);
    Optional<Company> findById(UUID id);
    boolean existsByCNPJ(String CNPJ);
    void deleteById(UUID id);
}
