package com.roberto.ecom.domain;

import java.io.Serializable;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;

import lombok.Getter;
import lombok.Setter;

@Entity
@Setter
@Getter
public class OrderItem implements Serializable {

    @EmbeddedId
    private OrderItemPK id = new OrderItemPK();

    private Double discount;

    private Integer amount;

    private Double price;

    public OrderItem() {
    }

    public OrderItem(Order order, Product product, Double discount, Integer amount, Double price) {
        this.id.setOrder(order);
        this.id.setProduct(product);
        this.discount = discount;
        this.amount = amount;
        this.price = price;
    }

    public Order getOrder() {
        return id.getOrder();
    }

    public Product getProduct() {
        return id.getProduct();
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
        OrderItem other = (OrderItem) obj;
        if (id == null) {
            if (other.id != null)
                return false;
        } else if (!id.equals(other.id))
            return false;
        return true;
    }
}