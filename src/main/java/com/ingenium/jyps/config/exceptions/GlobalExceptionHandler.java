package com.ingenium.jyps.config.exceptions;

import com.ingenium.jyps.config.exceptions.dto.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.HandlerMethodValidationException;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    // 1. Atrapa los errores de @Valid (Como el @PastOrPresent de tu Record)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleValidationExceptions(MethodArgumentNotValidException ex) {
        Map<String, String> errores = new HashMap<>();

        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String campo = ((FieldError) error).getField();
            String mensaje = error.getDefaultMessage();
            errores.put(campo, mensaje);
        });

        ErrorResponse response = new ErrorResponse(
                "Datos de entrada inválidos",
                HttpStatus.BAD_REQUEST.value(),
                System.currentTimeMillis(),
                errores
        );

        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(HandlerMethodValidationException.class)
    public ResponseEntity<ErrorResponse> handleValidationException(HandlerMethodValidationException ex) {
        Map<String, String> errores = new HashMap<>();

        // Spring 4 maximiza el uso de ValidationResult
        ex.getParameterValidationResults().forEach(result -> {
            String parametro = result.getMethodParameter().getParameterName();

            result.getResolvableErrors().forEach(error -> {
                // Buscamos el mensaje amigable
                String mensaje = error.getDefaultMessage();
                errores.put(parametro, mensaje);
            });
        });

        return ResponseEntity.badRequest().body(new ErrorResponse(
                "Error de validación",
                400,
                System.currentTimeMillis(),
                errores
        ));
    }

    // 2. Atrapa errores de lógica (Como el isAfter que pusimos manual)
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ErrorResponse> handleIllegalArgument(IllegalArgumentException ex) {
        ErrorResponse response = new ErrorResponse(
                ex.getMessage(),
                HttpStatus.BAD_REQUEST.value(),
                System.currentTimeMillis(),
                null
        );
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    // 3. Atrapa cualquier otro error inesperado (El "catch-all")
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleGlobalException(Exception ex) {
        ErrorResponse response = new ErrorResponse(
                ex.getMessage() != null ? ex.getMessage() : "Ocurrió un error inesperado",
                HttpStatus.INTERNAL_SERVER_ERROR.value(),
                System.currentTimeMillis(),
                null
        );
        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}