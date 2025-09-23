package com.supernova.inventario.exception;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.ConstraintViolationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.util.stream.Collectors;

@Slf4j
@Order(Ordered.LOWEST_PRECEDENCE)
// ⬇️ MUY IMPORTANTE: solo aplica a NUESTROS controllers, NO a springdoc
@RestControllerAdvice(basePackages = "com.supernova.inventario.controller")
public class ApiExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handleValidation(MethodArgumentNotValidException ex, HttpServletRequest req) {
        String msg = ex.getBindingResult().getFieldErrors().stream()
                .map(err -> err.getField()+": "+err.getDefaultMessage())
                .collect(Collectors.joining("; "));
        return base(HttpStatus.BAD_REQUEST, "Validación fallida: " + msg, req);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handleConstraint(ConstraintViolationException ex, HttpServletRequest req) {
        String msg = ex.getConstraintViolations().stream()
                .map(v -> v.getPropertyPath()+": "+v.getMessage())
                .collect(Collectors.joining("; "));
        return base(HttpStatus.BAD_REQUEST, msg, req);
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public ErrorResponse handleDup(DataIntegrityViolationException ex, HttpServletRequest req) {
        return base(HttpStatus.CONFLICT, "Conflicto de datos: " + ex.getMostSpecificCause().getMessage(), req);
    }

    // ⚠️ Deja este como última red, pero SOLO para nuestros controllers
    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ErrorResponse handleOther(Exception ex, HttpServletRequest req) {
        // log completo al console para diagnóstico
        log.error("Unhandled exception at {} -> {}: {}", req.getRequestURI(),
                ex.getClass().getName(), (ex.getMessage() == null ? "(sin mensaje)" : ex.getMessage()), ex);

        String msg = ex.getClass().getSimpleName() + ": " + (ex.getMessage() == null ? "(sin mensaje)" : ex.getMessage());
        return base(HttpStatus.INTERNAL_SERVER_ERROR, msg, req);
    }

    private ErrorResponse base(HttpStatus status, String msg, HttpServletRequest req) {
        return ErrorResponse.builder()
                .timestamp(Instant.now())
                .status(status.value())
                .error(status.getReasonPhrase())
                .message(msg)
                .path(req.getRequestURI())
                .build();
    }
}

