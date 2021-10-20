package com.roberto.ecom.domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Getter;
import lombok.Setter;

@Entity
public class Product implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Setter
    @Getter
    private Integer id;
    private String name;
    private double price;

    @JsonBackReference // deserialization works fine
    // @JsonIgnore //deserialization does not work, the reference will be null
    @ManyToMany
    @JoinTable(name = "PRODUCT_CATEGORY", 
        joinColumns = @JoinColumn(name = "PRODUCT_FK"), 
        inverseJoinColumns = @JoinColumn(name = "CATEGORY_FK")
    )
    @Setter
    @Getter
    private List<Category> categories = new ArrayList<>();

    @JsonIgnore
    @OneToMany(mappedBy = "id.product")
    @Setter
    @Getter
    private Set<OrderItem> items = new HashSet<>();

    public Product() {
    }

    public Product(Integer id, String name, double price) {
        this.id = id;
        this.name = name;
        this.price = price;
    }

    public void addCategories(Category... categories) {
        this.categories.addAll(Arrays.asList(categories));
    }

    public void addItems(OrderItem... items) {
        this.items.addAll(Arrays.asList(items));
    }

    @JsonIgnore
    public List<Order> getOrders() {
        return items.stream().map(i -> i.getOrder()).collect(Collectors.toList());
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
        Product other = (Product) obj;
        if (id == null) {
            if (other.id != null)
                return false;
        } else if (!id.equals(other.id))
            return false;
        return true;
    }
}
