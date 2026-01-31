package com.techservice.techservice.shared.exceptions;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;

public class ErrorResponse<T> {

    private final int status;
    private final String error;
    private final T message;
    private final Instant timestamp;

    public ErrorResponse(int status, String error, T message) {
        this.status = status;
        this.error = error;
        this.message = message;
        this.timestamp = Instant.now();
    }

    public int getStatus() {
        return status;
    }

    public String getError() {
        return error;
    }

    public T getMessage() {
        return message;
    }

    public Instant getTimestamp() {
        return timestamp;
    }
}
