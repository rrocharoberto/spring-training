package com.roberto.ecom.resources.exceptions;

import lombok.Getter;

@Getter
public class LoginError extends StandardError {

    String error;

    String path;

    public LoginError(Integer status, String error, String message, String path) {
        super(status, message, System.currentTimeMillis());
        this.error = error;
        this.path = path;
    }
}