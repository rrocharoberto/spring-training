package com.roberto.ecom.resources.exceptions;

import java.util.ArrayList;
import java.util.List;

import lombok.Getter;

public class ValidationError extends StandardError {
    
    @Getter
    private List<FieldMessage> errors = new ArrayList<>();

    public ValidationError(Integer status, String message, Long timestamp) {
        super(status, message, timestamp);
    }

    public void addError(String fieldName, String message){
        this.errors.add(new FieldMessage(fieldName, message));
    }
}
