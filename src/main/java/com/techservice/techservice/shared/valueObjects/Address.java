package com.techservice.techservice.shared.valueObjects;

import com.techservice.techservice.shared.exceptions.BusinessRuleException;
import jakarta.persistence.Embeddable;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Embeddable
public class Address {
    private  String zipCode;
    private  String street;
    private  String number;
    private  String neighborhood;
    private  String city;
    private  String complement;

    protected Address(){}

    public Address(@NotNull String zipCode,
                   @NotNull String street,
                   @NotNull String number,
                   @Nullable String neighborhood,
                   @Nullable String city,
                   @Nullable String complement) {
        String normalizedCep = normalizedCep(zipCode);

        if(normalizedCep.length() != 8)
            throw new BusinessRuleException("Invalid CEP");

        if(street == null || street.trim().isEmpty())
            throw new BusinessRuleException("Street must not be empty");

        if(number == null || number.trim().isEmpty())
            throw new BusinessRuleException("House number must not be empty");

        this.zipCode = normalizedCep;
        this.street = street.trim();
        this.number = number.trim();
        this.neighborhood = neighborhood != null ? neighborhood.trim() : null;
        this.city = city != null ? city.trim() : null;
        this.complement = complement != null ? complement.trim() : null;
    }

    private String normalizedCep(String zipCode){
        if (zipCode == null) return "";
        return zipCode.trim().replaceAll("[^0-9]", "");
    }

    @NotNull public String getZipCode() {return zipCode;}
    @NotNull public String getStreet() {return street;}
    @NotNull public String getNumber() {return number;}
    @Nullable public String getNeighborhood() {return neighborhood;}
    @Nullable public String getCity() {return city;}
    @Nullable public String getComplement() {return complement;}

}
