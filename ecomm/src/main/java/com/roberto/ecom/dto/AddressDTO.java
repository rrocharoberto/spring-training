package com.roberto.ecom.dto;

import java.io.Serializable;

import com.roberto.ecom.domain.Address;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class AddressDTO implements Serializable {

    private String street;

    private int number;

    private String extra;

    private String neighbour;

    private String zipCode;

    private String city;

    private String state;

    public AddressDTO() {
    }

    public AddressDTO(Address address) {
        this.street = address.getStreet();
        this.number = address.getNumber();
        this.extra = address.getExtra();
        this.neighbour = address.getNeighbour();
        this.zipCode = address.getZipCode();
        this.city = address.getCity().getName();
        this.state = address.getCity().getState().getName();
    }
}
