package com.roberto.ecom.domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import com.roberto.ecom.domain.enums.CustomerType;

import lombok.Getter;
import lombok.Setter;

@Entity
@Setter
@Getter
public class Customer implements Serializable {

    @Id
    private String id; // CPF ou CNPJ

    private String name;

    @Column(unique = true)
    private String email;

    //@Enumerated(EnumType.ORDINAL) //não funciona em conjunto com o AttributeConverter de Category
    private CustomerType type;

    @OneToMany(mappedBy = "customer", cascade = CascadeType.REMOVE)
    private List<Address> addresses = new ArrayList<>();

    @ElementCollection
    @CollectionTable(name = "PHONES")
    private Set<String> phones = new HashSet<>();

    @OneToMany(mappedBy = "customer")
    private List<Order> orders = new ArrayList<>();

    public Customer() {
    }

    public Customer(String id, String name, String email, CustomerType type) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.type = type;
    }

    public void addPhone(String phone) {
        this.phones.add(phone);
    }

    public void addPhones(String ... phones) {
        this.phones.addAll(Arrays.asList(phones));
    }

    public void addAddress(Address ... address) {
        this.addresses.addAll(Arrays.asList(address));
    }

    public void addOrders(Order ... orders) {
        this.orders.addAll(Arrays.asList(orders));
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
        Customer other = (Customer) obj;
        if (id == null) {
            if (other.id != null)
                return false;
        } else if (!id.equals(other.id))
            return false;
        return true;
    }
}