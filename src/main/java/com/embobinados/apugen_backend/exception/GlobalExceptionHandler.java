package com.embobinados.apugen_backend.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;

@RestControllerAdvice
public class GlobalExceptionHandler {

    // Usamos un 'record' de Java 21 para definir la estructura del JSON de error
    public record ErrorResponse(
            LocalDateTime timestamp,
            int status,
            String error,
            String message
    ) {}

    // Intercepta las RuntimeException (las que lanzamos en el ApuService)
    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<ErrorResponse> handleRuntimeException(RuntimeException ex) {

        ErrorResponse error = new ErrorResponse(
                LocalDateTime.now(),
                HttpStatus.BAD_REQUEST.value(),
                "Error en la Petición",
                ex.getMessage() // Aquí inyectará "Material no encontrado", etc.
        );

        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }
}