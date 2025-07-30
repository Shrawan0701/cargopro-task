package com.cargopro.loadbooking.exception;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.Instant;
import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<?> handleEntityNotFound(EntityNotFoundException ex) {
        return buildErrorResponse("Not Found", ex.getMessage(), 404);
    }

    @ExceptionHandler(IllegalStateException.class)
    public ResponseEntity<?> handleIllegalState(IllegalStateException ex) {
        return buildErrorResponse("Invalid State", ex.getMessage(), 400);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> handleGeneric(Exception ex) {
        return buildErrorResponse("Server Error", ex.getMessage(), 500);
    }

    private ResponseEntity<?> buildErrorResponse(String error, String message, int status) {
        Map<String, Object> errorMap = new HashMap<>();
        errorMap.put("timestamp", Instant.now());
        errorMap.put("status", status);
        errorMap.put("error", error);
        errorMap.put("message", message);
        return ResponseEntity.status(status).body(errorMap);
    }
}
