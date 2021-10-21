package com.roberto.ecom.resources.exceptions;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
public class FieldMessage implements Serializable {
    @Setter @Getter
    private String fieldName;
    @Setter @Getter
    private String message;
}
