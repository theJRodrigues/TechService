package com.techservice.techservice.modules.client.domain;

import com.techservice.techservice.modules.company.domain.Company;
import com.techservice.techservice.shared.enums.DocumentType;
import com.techservice.techservice.shared.exceptions.BusinessRuleException;
import com.techservice.techservice.shared.valueObjects.Address;
import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.Instant;
import java.util.UUID;
import java.util.stream.Stream;

@Entity
@Table(name = "clients")
public class Client {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "phone", nullable = false)
    private String phone;

    @Column(name = "email", nullable = false)
    private String email;

    @Column(name = "document_type", nullable = false)
    @Enumerated(EnumType.STRING)
    private DocumentType documentType;

    @Column(name = "document", nullable = false)
    private String document;

    @Embedded
    private Address address;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "company_id")
    private Company company;

    @Column(name = "is_active")
    private Boolean isActive = true;

    @CreationTimestamp
    @Column(name = "created_at")
    private Instant createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private Instant updatedAt;

    protected Client(){};

    public static Client create(String name,
                         String phone,
                         String email,
                         DocumentType documentType,
                         String document,
                         Address address,
                         Company company) {

        validateNonEmpty(name, "Name");
        validateNonEmpty(phone, "Phone");
        validateNonEmpty(email, "Email");
        validateNonNull(address, "Address");
        validateNonNull(company, "Company");

        validateDocument(documentType, document);


        Client newClient = new Client();
        newClient.name = name.trim();
        newClient.phone = phone.replaceAll("\\D", "");
        newClient.email = email.trim();
        newClient.documentType = documentType;
        newClient.document = document.replaceAll("\\D", "");
        newClient.address = address;
        newClient.company = company;

        return newClient;
    }

    public void deactivate(){
        if(Boolean.FALSE.equals(isActive))
            throw new BusinessRuleException("Client is already inactive");

        this.isActive = false;
    }

    public void update (String name,
                        String phone,
                        String email
                        ) {
        if (Stream.of(name, phone, email).allMatch( v -> v ==null || v.isBlank()))
            throw new BusinessRuleException("At least one field must be provided");

        if (name != null && !name.isBlank())
            this.name = name.trim();

        if (phone != null && !phone.isBlank())
            this.phone = phone.replaceAll("\\D", "");

        if (email != null && !email.isBlank())
            this.email = email.trim();
    }

    public void updateAddress(String zipCode,
                              String street,
                              String number,
                              String neighborhood,
                              String city,
                              String complement) {
        this.address = Address.create(
                zipCode,
                street,
                number,
                neighborhood,
                city,
                complement
        );
    }

    private static void validateNonEmpty(String value, String field){
        if (value == null || value.trim().isEmpty())
            throw new BusinessRuleException(field + " must not be empty");
    }

    private static void validateNonNull(Object value, String field){
        if(value == null)
            throw new BusinessRuleException(field + " must not be null");
    }

    private static void validateDocument(DocumentType type, String document){
        if(type == null)
            throw new BusinessRuleException("Document type must not be empty");
        if(document == null || document.trim().isEmpty())
            throw new BusinessRuleException("Document must not be empty");

        String normalized = document.replaceAll("\\D", "");
        if(type == DocumentType.CPF && normalized.length() != 11)
            throw new BusinessRuleException("Invalid CPF");

        if(type == DocumentType.CNPJ && normalized.length() != 14)
            throw new BusinessRuleException("Invalid CNPJ");
    }

    public UUID getId() {return id;}
    public String getName() {return name;}
    public String getPhone() {return phone;}
    public String getEmail() {return email;}
    public DocumentType getDocumentType() {return documentType;}
    public String getDocument() {return document;}
    public Address getAddress() {return address;}
    public Company getCompany() {return company;}
    public Boolean isActive() {return isActive;}
    public Instant getCreatedAt() {return createdAt;}
    public Instant getUpdatedAt() {return updatedAt;}
}
