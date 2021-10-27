package com.roberto.ecom.resources.exceptions;

import javax.servlet.http.HttpServletRequest;

import com.roberto.ecom.services.exceptions.AuthorizationException;
import com.roberto.ecom.services.exceptions.ECommerceException;
import com.roberto.ecom.services.exceptions.ObjectNotFoundException;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.MailAuthenticationException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class ResourceExceptionHandler {

    @ExceptionHandler(ObjectNotFoundException.class)
    public ResponseEntity<StandardError> objectNotFound(ObjectNotFoundException e, HttpServletRequest request) {
        StandardError err = new StandardError(HttpStatus.NOT_FOUND.value(), e.getMessage(), System.currentTimeMillis());

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(err);
    }

    @ExceptionHandler(ECommerceException.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public StandardError objectNotFound(ECommerceException e, HttpServletRequest request) {
        return new StandardError(HttpStatus.BAD_REQUEST.value(), e.getMessage(), System.currentTimeMillis());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ValidationError objectNotFound(MethodArgumentNotValidException e, HttpServletRequest request) {

        ValidationError error = new ValidationError(HttpStatus.BAD_REQUEST.value(), "Validation error.", System.currentTimeMillis());
        e.getBindingResult().getFieldErrors().stream()
                .forEach(err -> error.addError(err.getField(), err.getDefaultMessage()));
        return error;
    }

    @ExceptionHandler(AuthorizationException.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public StandardError authorization(AuthorizationException e, HttpServletRequest request) {
        return new StandardError(HttpStatus.FORBIDDEN.value(), e.getMessage(), System.currentTimeMillis());
    }

    @ExceptionHandler(MailAuthenticationException.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.BAD_GATEWAY)
    public StandardError authorization(MailAuthenticationException e, HttpServletRequest request) {
        return new StandardError(HttpStatus.BAD_GATEWAY.value(), e.getMessage(), System.currentTimeMillis());
    }

    @ExceptionHandler(Exception.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public StandardError authorization(Exception e, HttpServletRequest request) {
        return new StandardError(HttpStatus.INTERNAL_SERVER_ERROR.value(), e.getMessage(), System.currentTimeMillis());
    }   
}