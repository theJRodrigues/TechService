package com.gestaoTI.gestaoTI.shared.exceptions;

import jakarta.validation.ConstraintViolationException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionsHandler {
    @ExceptionHandler(EmailAlreadyExistsException.class)
    public ResponseEntity<ErrorResponse<?>> handleEmailAlreadyExists(EmailAlreadyExistsException ex){

        ErrorResponse<String> error = new ErrorResponse<>(
                HttpStatus.CONFLICT.value(),
                "BUSINESS_RULE_VIOLATION",
                ex.getMessage()
        );
        return ResponseEntity.status(HttpStatus.CONFLICT).body(error);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse<?>> handleValidationError(MethodArgumentNotValidException ex){
        Map<String, String>  errorsMap = new HashMap<>();

        ex.getBindingResult().getFieldErrors().forEach(e ->{
            errorsMap.put(e.getField(), e.getDefaultMessage());
        });

        ErrorResponse<Map<String, String>> errors = new ErrorResponse<>(
                HttpStatus.BAD_REQUEST.value(),
                "VALIDATION_ERROR",
                errorsMap
        );

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errors);
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<ErrorResponse<?>> handleDataIntegrityViolation( DataIntegrityViolationException ex){
        ErrorResponse<String> error = new ErrorResponse<>(
                HttpStatus.CONFLICT.value(),
                "DATABASE_CONSTRAINT",
                "Database constraints violation"
        );
        return ResponseEntity.status(HttpStatus.CONFLICT).body(error);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ErrorResponse<?>> handleConstraintViolation(ConstraintViolationException ex){

        Map<String, String> errors = new HashMap<>();

        ex.getConstraintViolations().forEach(v ->{
            errors.put(v.getPropertyPath().toString(), v.getMessage());
        });

        ErrorResponse<Map<String, String>> error = new ErrorResponse<>(
                HttpStatus.BAD_REQUEST.value(),
                "VALIDATION_ERROR",
                errors
        );
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse<?>> handleGenericExceptions(Exception ex){
        ErrorResponse<String> error = new ErrorResponse<>(
                HttpStatus.INTERNAL_SERVER_ERROR.value(),
                "INTERNAL_ERROR",
                "Internal server error"
        );

        System.out.println(ex.getMessage());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
    }

}
