package com.roberto.ecom.domain;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "ORDERS")
public class Order implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Setter
    @Getter
    private Integer id;

    @JsonFormat(pattern = "dd/MM/yyyy HH:mm:ss")
    @Setter
    @Getter
    private LocalDateTime dateRequest;

    @JsonManagedReference
    @OneToOne(mappedBy = "order", cascade = CascadeType.ALL) //o cascade é para salvar o pagamento automaticamente quando salva o pedido
    @Setter
    @Getter
    private Payment payment;

    @JsonManagedReference
    @ManyToOne
    @JoinColumn(name = "CUSTOMER_FK")
    @Setter
    @Getter
    private Customer customer;

    @ManyToOne
    @JoinColumn(name = "SHIP_ADDRESS_FK")
    @Setter
    @Getter
    private Address shippingAddress;

    @OneToMany(mappedBy = "id.order")
    @Setter
    @Getter
    private Set<OrderItem> items = new HashSet<>();

    public Order() {
    }

    public Order(Integer id, LocalDateTime dateRequest, Customer customer, Address shippingAddress) {
        this.id = id;
        this.dateRequest = dateRequest;
        this.customer = customer;
        this.shippingAddress = shippingAddress;
    }
    
    @JsonIgnore
    public List<Product> getProducts() {
        return items.stream().map(i -> i.getProduct()).collect(Collectors.toList());
    }

    public void addItems(OrderItem ... items) {
        this.items.addAll(Arrays.asList(items));
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
        Order other = (Order) obj;
        if (id == null) {
            if (other.id != null)
                return false;
        } else if (!id.equals(other.id))
            return false;
        return true;
    }
}
