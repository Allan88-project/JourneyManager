package com.journeymanager.journeybackend.exception;

import jakarta.persistence.EntityNotFoundException;
import jakarta.servlet.http.HttpServletRequest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    /*
     * 404 - Resource Not Found
     */
    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<?> handleNotFound(
            EntityNotFoundException ex,
            HttpServletRequest request) {

        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(Map.of(
                        "timestamp", LocalDateTime.now(),
                        "status", 404,
                        "error", "Not Found",
                        "message", ex.getMessage(),
                        "path", request.getRequestURI()
                ));
    }

    /*
     * 400 - Business Rule Violation
     */
    @ExceptionHandler(IllegalStateException.class)
    public ResponseEntity<?> handleIllegalState(
            IllegalStateException ex,
            HttpServletRequest request) {

        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(Map.of(
                        "timestamp", LocalDateTime.now(),
                        "status", 400,
                        "error", "Bad Request",
                        "message", ex.getMessage(),
                        "path", request.getRequestURI()
                ));
    }

    /*
     * 403 - Access Denied
     */
    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<?> handleAccessDenied(
            AccessDeniedException ex,
            HttpServletRequest request) {

        return ResponseEntity.status(HttpStatus.FORBIDDEN)
                .body(Map.of(
                        "timestamp", LocalDateTime.now(),
                        "status", 403,
                        "error", "Forbidden",
                        "message", ex.getMessage(),
                        "path", request.getRequestURI()
                ));
    }

    /*
     * 500 - Unexpected Errors
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> handleGeneric(
            Exception ex,
            HttpServletRequest request) {

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(Map.of(
                        "timestamp", LocalDateTime.now(),
                        "status", 500,
                        "error", "Internal Server Error",
                        "message", ex.getMessage(),
                        "path", request.getRequestURI()
                ));
    }
}