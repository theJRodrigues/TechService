package com.techservice.techservice.modules.company.infra.persistence;

import com.techservice.techservice.modules.company.domain.Company;
import com.techservice.techservice.modules.company.domain.CompanyRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface JPACompanyRepository extends JpaRepository<Company, UUID>, CompanyRepository {
}
