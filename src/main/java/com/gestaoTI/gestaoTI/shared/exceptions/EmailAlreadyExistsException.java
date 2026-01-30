package com.gestaoTI.gestaoTI.shared.exceptions;

public class EmailAlreadyExistsException extends RuntimeException {
    public EmailAlreadyExistsException() {
        super("E-mail already exists");
    }
}
