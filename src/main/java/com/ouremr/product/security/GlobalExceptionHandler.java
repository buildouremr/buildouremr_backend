package com.ouremr.product.security;

import com.ouremr.product.emrbean.EMRResponseBean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.stream.Collectors;

/**
 * Global exception handler for the application.
 * Catches all unhandled exceptions and returns consistent JSON responses.
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    /**
     * Handle validation errors from @Valid annotations.
     */
    @ExceptionHandler({MethodArgumentNotValidException.class})
    public ResponseEntity<EMRResponseBean> handleValidationErrors(MethodArgumentNotValidException ex) {
        String errors = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(error -> error.getField() + ": " + error.getDefaultMessage())
                .collect(Collectors.joining(", "));

        EMRResponseBean response = new EMRResponseBean();
        response.setData(errors);
        response.setStatus("FAILED");
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    /**
     * Handle type mismatches like sending "null" string to an Integer parameter.
     */
    @ExceptionHandler(org.springframework.web.method.annotation.MethodArgumentTypeMismatchException.class)
    public ResponseEntity<EMRResponseBean> handleTypeMismatch(org.springframework.web.method.annotation.MethodArgumentTypeMismatchException ex) {
        EMRResponseBean response = new EMRResponseBean();
        response.setData("Invalid parameter value for " + ex.getName() + ": " + ex.getValue());
        response.setStatus("FAILED");
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    /**
     * Handle IllegalArgumentException for bad input.
     */
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<EMRResponseBean> handleIllegalArgument(IllegalArgumentException ex) {
        EMRResponseBean response = new EMRResponseBean();
        response.setData(ex.getMessage());
        response.setStatus("FAILED");
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    /**
     * Handle NullPointerException.
     */
    @ExceptionHandler(NullPointerException.class)
    public ResponseEntity<EMRResponseBean> handleNullPointer(NullPointerException ex) {
        log.error("Null pointer exception: {}", ex.getMessage(), ex);
        EMRResponseBean response = new EMRResponseBean();
        response.setData("A required value was null");
        response.setStatus("FAILED");
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
    }

    /**
     * Handle EntityNotFoundException / NoResultException.
     */
    @ExceptionHandler({jakarta.persistence.EntityNotFoundException.class, jakarta.persistence.NoResultException.class})
    public ResponseEntity<EMRResponseBean> handleEntityNotFound(Exception ex) {
        EMRResponseBean response = new EMRResponseBean();
        response.setData("Requested resource was not found");
        response.setStatus("FAILED");
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
    }

    /**
     * Handle MissingServletRequestParameterException.
     */
    @ExceptionHandler(org.springframework.web.bind.MissingServletRequestParameterException.class)
    public ResponseEntity<EMRResponseBean> handleMissingParameter(org.springframework.web.bind.MissingServletRequestParameterException ex) {
        EMRResponseBean response = new EMRResponseBean();
        response.setData("Missing required parameter: " + ex.getParameterName());
        response.setStatus("FAILED");
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    /**
     * Handle RuntimeException for application logic errors.
     */
    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<EMRResponseBean> handleRuntimeException(RuntimeException ex) {
        log.error("Unhandled runtime exception: {}", ex.getMessage(), ex);
        EMRResponseBean response = new EMRResponseBean();
        response.setData(ex.getMessage() != null ? ex.getMessage() : "An unexpected error occurred");
        response.setStatus("FAILED");
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<EMRResponseBean> handleGenericException(Exception ex) {
        log.error("Unhandled exception: {}", ex.getMessage(), ex);
        EMRResponseBean response = new EMRResponseBean();
        response.setData("Internal server error: " + ex.getMessage());
        response.setStatus("FAILED");
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
    }
}
