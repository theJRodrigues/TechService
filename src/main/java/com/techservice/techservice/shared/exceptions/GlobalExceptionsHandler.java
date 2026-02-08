package com.techservice.techservice.shared.exceptions;

import jakarta.validation.ConstraintViolationException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionsHandler {

    @ExceptionHandler(BusinessRuleException.class)
    public ResponseEntity<ErrorResponse<?>> handleCNPJAlreadyExists(BusinessRuleException ex){

        ErrorResponse<String> error = new ErrorResponse<>(
                HttpStatus.CONFLICT.value(),
                "BUSINESS_RULE_VIOLATION",
                ex.getMessage()
        );
        return ResponseEntity.status(HttpStatus.CONFLICT).body(error);
    }

    @ExceptionHandler(ForbiddenException.class)
    public ResponseEntity<ErrorResponse<?>> handleForbiddenException(ForbiddenException ex){
        ErrorResponse<String> error = new ErrorResponse<>(
                HttpStatus.FORBIDDEN.value(),
                "FORBIDDEN",
                ex.getMessage()
        );
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(error);
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

        return ResponseEntity.badRequest().body(errors);
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

    @ExceptionHandler(InvalidTokenContextException.class)
    public ResponseEntity<ErrorResponse<?>> handleIllegalStateException(InvalidTokenContextException ex){
        ErrorResponse<String> error = new ErrorResponse<>(
                HttpStatus.UNAUTHORIZED.value(),
                "INVALID_TOKEN_CONTEXT",
                ex.getMessage()
        );

        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(error);
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<ErrorResponse<?>> handleMisMatchException(MethodArgumentTypeMismatchException ex){
        ErrorResponse<String> error = new ErrorResponse<>(
                HttpStatus.BAD_REQUEST.value(),
                "INVALID_ARGUMENT",
                "Invalid valur for parameter  " + ex.getName()
        );
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
    }

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<ErrorResponse<?>> handleEntityNotFoundException(NotFoundException ex){
        ErrorResponse<String> error = new ErrorResponse<>(
                HttpStatus.NOT_FOUND.value(),
                "NOT_FOUND",
                ex.getMessage()
        );

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
    }
}
