package com.exercise.userapi.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.ArrayList;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    private static final String MESSAGE_KEY = "message";

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> handleValidationExceptions(MethodArgumentNotValidException ex) {
       final var errors = new ArrayList<String>();
        ex.getBindingResult().getFieldErrors().forEach(error -> {
            final var message =  error.getField() + " " + error.getDefaultMessage();
            errors.add(message);
        });
        final var message = String.join(", ", errors);
        return ResponseEntity.badRequest().body(Map.of(MESSAGE_KEY, message));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Map<String, String>> handleGeneralExceptions(Exception ex) {
        return ResponseEntity.internalServerError().body(Map.of(MESSAGE_KEY, "Internal Server Error"));
    }

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<Map<String, String>> handleRuntimeException(UserNotFoundException ex) {
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(Map.of(MESSAGE_KEY, ex.getMessage()));
    }
}
