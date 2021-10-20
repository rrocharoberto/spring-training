package com.roberto.ecom.domain;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.fasterxml.jackson.annotation.JsonBackReference;

import lombok.Getter;
import lombok.Setter;

@Entity
public class Address implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Setter
    @Getter
    private Integer id;

    @Setter
    @Getter
    private String street;

    @Setter
    @Getter
    private int number;

    @Setter
    @Getter
    private String extra;

    @Setter
    @Getter
    private String neighbour;

    @Setter
    @Getter
    private String zipCode;

    @JsonBackReference
    @ManyToOne
    @JoinColumn(name = "CUSTOMER_FK")
    @Setter
    @Getter
    private Customer customer;

    @ManyToOne
    @JoinColumn(name = "CITY_FK")
    @Setter
    @Getter
    private City city;

    public Address() {
    }

    public Address(Integer id, String street, int number, String extra, String neighbour, String zipCode,
            Customer customer, City city) {
        this.id = id;
        this.street = street;
        this.number = number;
        this.extra = extra;
        this.neighbour = neighbour;
        this.zipCode = zipCode;
        this.customer = customer;
        this.city = city;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Address other = (Address) obj;
        if (id == null) {
            if (other.id != null)
                return false;
        } else if (!id.equals(other.id))
            return false;
        return true;
    }
}
