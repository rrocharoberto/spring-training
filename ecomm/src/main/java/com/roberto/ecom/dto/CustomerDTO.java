package com.roberto.ecom.dto;

import java.util.ArrayList;
import java.util.List;

import com.roberto.ecom.domain.Customer;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class CustomerDTO {

    private String id;

    private String name;

    private String email;

    private Integer type;

    private List<String> phones = new ArrayList<>();

    private List<AddressDTO> addresses = new ArrayList<>();

    private List<String> profiles = new ArrayList<>();

    public CustomerDTO(Customer customer) {
        this.id = customer.getId();
        this.name = customer.getName();
        this.email = customer.getEmail();
        this.type = customer.getType().getCode();
        phones.addAll(customer.getPhones());
        customer.getProfiles().forEach(p -> profiles.add(p.toString()));
        customer.getAddresses().forEach(a -> addresses.add(new AddressDTO(a)));
    }
}