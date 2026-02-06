package com.techservice.techservice.shared.exceptions;

public class BusinessRuleException extends RuntimeException {
    public BusinessRuleException(){super("Business rule violation");}
    public BusinessRuleException(String message) {
        super(message);
    }
}
