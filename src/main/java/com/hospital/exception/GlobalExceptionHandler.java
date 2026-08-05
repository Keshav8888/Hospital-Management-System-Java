package com.hospital.exception;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import jakarta.servlet.http.HttpServletRequest;

@RestControllerAdvice
public class GlobalExceptionHandler {

	@ExceptionHandler(ResourceNotFoundException.class)
	public ResponseEntity<ErrorResponse> handleResourceNotFound(ResourceNotFoundException ex, HttpServletRequest request) {

	    ErrorResponse error = new ErrorResponse();

	    error.setTimestamp(LocalDateTime.now());

	    error.setStatus(HttpStatus.NOT_FOUND.value());

	    error.setError(HttpStatus.NOT_FOUND.getReasonPhrase());

	    error.setMessage(ex.getMessage());

	    error.setPath(request.getRequestURI());

	    return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
	}
	
	@ExceptionHandler(DuplicateResourceException.class)
	public ResponseEntity<ErrorResponse> handleDuplicateResource(DuplicateResourceException ex, HttpServletRequest request) {

	    ErrorResponse error = new ErrorResponse();

	    error.setTimestamp(LocalDateTime.now());

	    error.setStatus(HttpStatus.CONFLICT.value());

	    error.setError(HttpStatus.CONFLICT.getReasonPhrase());

	    error.setMessage(ex.getMessage());

	    error.setPath(request.getRequestURI());

	    return new ResponseEntity<>(error, HttpStatus.CONFLICT);
	}
	
    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<ErrorResponse> handleRuntimeException(RuntimeException ex, HttpServletRequest request) {

        ErrorResponse error = new ErrorResponse();

        error.setTimestamp(LocalDateTime.now());

        error.setStatus(HttpStatus.BAD_REQUEST.value());

        error.setError(HttpStatus.BAD_REQUEST.getReasonPhrase());

        error.setMessage(ex.getMessage());

        error.setPath(request.getRequestURI());

        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> handleValidationException(MethodArgumentNotValidException ex) {

        Map<String, String> errors = new HashMap<>();

        for (FieldError error : ex.getBindingResult().getFieldErrors()) {

            errors.put(error.getField(), error.getDefaultMessage());
        }

        return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleException(Exception ex, HttpServletRequest request) {

        ErrorResponse error = new ErrorResponse();

        error.setTimestamp(LocalDateTime.now());

        error.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());

        error.setError(HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase());

        error.setMessage(ex.getMessage());

        error.setPath(request.getRequestURI());

        return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}