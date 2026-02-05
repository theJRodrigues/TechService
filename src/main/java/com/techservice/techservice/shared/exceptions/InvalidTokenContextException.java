package com.techservice.techservice.shared.exceptions;

public class InvalidTokenContextException extends RuntimeException {
    public InvalidTokenContextException(){ super("Token context is no longer valid. Please log in again.");}
    public InvalidTokenContextException(String message) {
        super(message);
    }
}
