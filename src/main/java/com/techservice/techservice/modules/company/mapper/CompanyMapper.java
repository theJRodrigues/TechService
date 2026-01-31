package com.techservice.techservice.modules.company.mapper;

import com.techservice.techservice.modules.company.domain.Company;
import com.techservice.techservice.modules.company.dto.CreateCompanyRequest;

public class CompanyMapper  {
    public static Company toEntity(CreateCompanyRequest dto){
        return new Company(dto.companyName(), dto.CNPJ());
    }

}
