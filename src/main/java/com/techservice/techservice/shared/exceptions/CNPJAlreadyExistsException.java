package com.techservice.techservice.shared.exceptions;

public class CNPJAlreadyExistsException extends RuntimeException {
    public CNPJAlreadyExistsException() { super("CNPJ already exists");}
}
