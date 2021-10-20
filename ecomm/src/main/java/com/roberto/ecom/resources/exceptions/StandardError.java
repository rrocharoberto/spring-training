package com.roberto.ecom.resources.exceptions;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
public class StandardError implements Serializable {
    @Setter @Getter
    private Integer status;
    @Setter @Getter
    private String message;
    @Setter @Getter
    private Long timestamp;
}
