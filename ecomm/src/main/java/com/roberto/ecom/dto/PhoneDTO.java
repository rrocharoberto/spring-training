package com.roberto.ecom.dto;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class PhoneDTO {

    @NotNull(message = "Phone number can not be null.")
    @NotEmpty(message = "Phone number can not be empty.")
    @Size(min = 8, max = 20, message = "The size must be between 5 and 20.")
    private String number;

    public PhoneDTO(String number) {
        this.number = number;
    }
}