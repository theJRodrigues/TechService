package com.techservice.techservice.shared.exceptions;

public class ForbiddenException extends RuntimeException {
    public ForbiddenException(){super("Forbbiden");}
    public ForbiddenException(String message) {
        super(message);
    }
}
