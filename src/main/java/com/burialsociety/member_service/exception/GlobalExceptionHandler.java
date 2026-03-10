package com.burialsociety.member_service.exception;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.resource.NoResourceFoundException;

import java.time.OffsetDateTime;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice // This class "advises" all Controllers
public class GlobalExceptionHandler {

    // 1. Handles our custom "Not Found" exception
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleResourceNotFound(ResourceNotFoundException ex) {
        ErrorResponse error = new ErrorResponse(
                OffsetDateTime.now(),
                HttpStatus.NOT_FOUND.value(),
                ex.getMessage()
        );
        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }

    // 2. Handles validation errors from @Valid in the Controller
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ValidationErrorResponse> handleValidationErrors(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach(error -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });

        ValidationErrorResponse errorResponse = new ValidationErrorResponse(
                OffsetDateTime.now(),
                HttpStatus.BAD_REQUEST.value(),
                "Validation Failed",
                errors
        );
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    // 3. NEW: Handles Database Constraints (e.g., Duplicate ID Numbers)
    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<ErrorResponse> handleDataIntegrityViolation(DataIntegrityViolationException ex) {
        String errorMessage = "A database constraint was violated.";

        // Extract the root cause message from PostgreSQL
        Throwable rootCause = ex.getMostSpecificCause();
        if (rootCause != null && rootCause.getMessage() != null) {
            String rootMessage = rootCause.getMessage();

            if (rootMessage.contains("duplicate key value violates unique constraint")) {
                // Check specifically for the ID Number constraint we saw in your logs
                if (rootMessage.contains("personal_details_id_number_key")) {
                    errorMessage = "A member with this ID number already exists.";
                }
                // You can add more specific constraints here later if needed
                else if (rootMessage.contains("contact_details_email_key")) {
                    errorMessage = "A member with this email address already exists.";
                } else {
                    errorMessage = "A record with this unique identifier already exists.";
                }
            }
        }

        ErrorResponse error = new ErrorResponse(
                OffsetDateTime.now(),
                HttpStatus.CONFLICT.value(), // 409 Conflict is standard for duplicate data
                errorMessage
        );
        return new ResponseEntity<>(error, HttpStatus.CONFLICT);
    }

    // 4. NEW: Handles bad HTTP methods (e.g., GET instead of POST) - cleans up your logs
    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public ResponseEntity<ErrorResponse> handleMethodNotSupported(HttpRequestMethodNotSupportedException ex) {
        ErrorResponse error = new ErrorResponse(
                OffsetDateTime.now(),
                HttpStatus.METHOD_NOT_ALLOWED.value(),
                "HTTP Method not supported: " + ex.getMethod()
        );
        return new ResponseEntity<>(error, HttpStatus.METHOD_NOT_ALLOWED);
    }

    // 5. NEW: Handles missing static resources (like bots looking for favicon.ico) - cleans up your logs
    @ExceptionHandler(NoResourceFoundException.class)
    public ResponseEntity<ErrorResponse> handleNoResourceFound(NoResourceFoundException ex) {
        ErrorResponse error = new ErrorResponse(
                OffsetDateTime.now(),
                HttpStatus.NOT_FOUND.value(),
                "Resource not found: " + ex.getResourcePath()
        );
        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }

    // --- The nested record classes have been removed from here ---
}