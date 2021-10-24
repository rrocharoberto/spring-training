package com.roberto.ecom.dto;

import java.util.Set;

import javax.validation.constraints.Email;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;
import javax.validation.constraints.Size;

import com.roberto.ecom.services.validation.CustomerGroups;
import com.roberto.ecom.services.validation.NewCustomerGroupSequenceProvider;

import org.hibernate.validator.constraints.br.CNPJ;
import org.hibernate.validator.constraints.br.CPF;
import org.hibernate.validator.group.GroupSequenceProvider;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@GroupSequenceProvider(NewCustomerGroupSequenceProvider.class)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class CustomerNewDTO {

    @CPF(groups = CustomerGroups.CPFGroup.class, message = "Invalid CPF number.")
    @CNPJ(groups = CustomerGroups.CNPJGroup.class, message = "Invalid CNPJ number.")
    private String id;

    @NotNull(message = "Value can not be null.")
    @NotEmpty(message = "Value can not be empty.")
    @Size(min = 5, max = 80, message = "The size must be between 5 and 80.")
    private String name;

    @NotNull(message = "Value can not be null.")
    @NotEmpty(message = "Value can not be empty.")
    @Email(message = "Invalid email.")
    private String email;

    @NotNull(message = "Value can not be null.")
    private Integer type;

    @NotEmpty(message = "Value can not be empty.")
    private String street;

    @NotNull(message = "Value can not be null.")
    @PositiveOrZero
    private Integer number;

    private String extra;

    @NotEmpty(message = "Value can not be empty.")
    private String neighbour;

    @NotEmpty(message = "Value can not be empty.")
    private String zipCode;

    @NotNull(message = "Value can not be null.")
    @Min(0)
    private Integer cityId;

    @NotEmpty(message = "Value can not be empty.")
    private String password;

    @NotEmpty(message = "Value can not be empty.")
    private Set<PhoneDTO> phones;
}
