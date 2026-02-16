package com.techservice.techservice.shared.valueObjects;

import com.techservice.techservice.shared.exceptions.BusinessRuleException;
import jakarta.persistence.Embeddable;

@Embeddable
public class Address {
    private  String zipCode;
    private  String street;
    private  String number;
    private  String neighborhood;
    private  String city;
    private  String complement;

    protected Address(){}

    public Address(String zipCode,
                   String street,
                   String number,
                   String neighborhood,
                   String city,
                   String complement) {
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
        this.neighborhood = neighborhood;
        this.city = city;
        this.complement = complement;
    }

    private String normalizedCep(String zipCode){
        if (zipCode == null) return "";
        return zipCode.trim().replaceAll("[^0-9]", "");
    }

    public String getZipCode() {return zipCode;}
    public String getStreet() {return street;}
    public String getNumber() {return number;}
    public String getNeighborhood() {return neighborhood;}
    public String getCity() {return city;}
    public String getComplement() {return complement;}

}
