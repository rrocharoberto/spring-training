package com.roberto.ecom.dto;

import javax.validation.constraints.Email;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class EmailDTO {

    @Email(message = "Invalid email.")
    private String email;
}
