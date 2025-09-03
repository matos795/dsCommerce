package com.devsuperior.dsCommerce.controllers.handlers;

import java.time.Instant;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.devsuperior.dsCommerce.dto.CustomError;
import com.devsuperior.dsCommerce.dto.ValidationError;
import com.devsuperior.dsCommerce.services.exceptions.DatabaseException;
import com.devsuperior.dsCommerce.services.exceptions.ResourceNotFoundException;

import jakarta.servlet.http.HttpServletRequest;

@ControllerAdvice
public class ControllerExceptionHandler {

@ExceptionHandler(ResourceNotFoundException.class)
public ResponseEntity<CustomError> resourceNotFound(ResourceNotFoundException e, HttpServletRequest request) {
HttpStatus status = HttpStatus.NOT_FOUND;
CustomError err = new CustomError(Instant.now(), status.value(), e.getMessage(), request.getRequestURI());
return ResponseEntity.status(status).body(err);
}

@ExceptionHandler(DatabaseException.class)
public ResponseEntity<CustomError> databaseException(DatabaseException e, HttpServletRequest request) {
HttpStatus status = HttpStatus.BAD_REQUEST;
CustomError err = new CustomError(Instant.now(), status.value(), e.getMessage(), request.getRequestURI());
return ResponseEntity.status(status).body(err);
}

@ExceptionHandler(MethodArgumentNotValidException.class)
public ResponseEntity<CustomError> methodArgumentNotValid(MethodArgumentNotValidException e, HttpServletRequest request) {
HttpStatus status = HttpStatus.UNPROCESSABLE_ENTITY;
ValidationError err = new ValidationError(Instant.now(), status.value(), "Dados Inválidos!", request.getRequestURI());

for (FieldError fieldError : e.getBindingResult().getFieldErrors()) {
    err.addError(fieldError.getField(), fieldError.getDefaultMessage());
}
return ResponseEntity.status(status).body(err);
}
}