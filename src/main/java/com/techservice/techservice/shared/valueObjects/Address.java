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

        validateNonEmpty(zipCode, "zipCode");
        validateNonEmpty(street, "Street");
        validateNonEmpty(number, "Number");

        String normalizedZipCode = zipCode.replaceAll("\\D", "");
        if(normalizedZipCode.length() != 8)
            throw new BusinessRuleException("Invalid zipCode");
        
        this.zipCode = normalizedZipCode;
        this.street = street.trim();
        this.number = number.trim();
        this.neighborhood = neighborhood != null ? neighborhood.trim() : null;
        this.city = city != null ? city.trim() : null;
        this.complement = complement != null ? complement.trim() : null;
    }

    private static void validateNonEmpty(String value, String field){
        if (value == null || value.trim().isEmpty())
            throw new BusinessRuleException(field + " must not be empty");
    }

    private static void validateNonNull(Object value, String field){
        if(value == null)
            throw new BusinessRuleException(field + " must not be null");
    }

    @NotNull public String getZipCode() {return zipCode;}
    @NotNull public String getStreet() {return street;}
    @NotNull public String getNumber() {return number;}
    @Nullable public String getNeighborhood() {return neighborhood;}
    @Nullable public String getCity() {return city;}
    @Nullable public String getComplement() {return complement;}

}
