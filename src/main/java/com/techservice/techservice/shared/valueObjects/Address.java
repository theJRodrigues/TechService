package com.techservice.techservice.shared.valueObjects;

import com.techservice.techservice.shared.exceptions.BusinessRuleException;
import jakarta.persistence.Embeddable;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Objects;
import java.util.stream.Stream;

@Embeddable
public class Address {
    private  String zipCode;
    private  String street;
    private  String number;
    private  String neighborhood;
    private  String city;
    private  String complement;

    protected Address(){}

    public static Address create(@NotNull String zipCode,
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

        Address address = new Address();
        address.zipCode = normalizedZipCode;
        address.street = street.trim();
        address.number = number.trim();
        address.neighborhood = neighborhood != null ? neighborhood.trim() : null;
        address.city = city != null ? city.trim() : null;
        address.complement = complement != null ? complement.trim() : null;

        return address;
    }


    private String resolve(String newValue, String currentValue) {
        if (newValue == null || newValue.isBlank())
            return currentValue;

        return newValue.trim();
    }

    private static void validateNonEmpty(String value, String field){
        if (value == null || value.trim().isEmpty())
            throw new BusinessRuleException(field + " must not be empty");
    }

    @NotNull public String getZipCode() {return zipCode;}
    @NotNull public String getStreet() {return street;}
    @NotNull public String getNumber() {return number;}
    @Nullable public String getNeighborhood() {return neighborhood;}
    @Nullable public String getCity() {return city;}
    @Nullable public String getComplement() {return complement;}

}
