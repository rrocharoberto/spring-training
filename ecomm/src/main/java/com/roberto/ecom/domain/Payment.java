package com.roberto.ecom.domain;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.MapsId;
import javax.persistence.OneToOne;

import com.roberto.ecom.domain.enums.PaymentStatus;

import lombok.Getter;
import lombok.Setter;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@Setter
@Getter
public abstract class Payment implements Serializable {

    @Id
    private Integer id;

    private PaymentStatus status;

    @OneToOne
    @JoinColumn(name = "ORDER_FK")
    @MapsId
    private Order order;

    public Payment() {
    }

    public Payment(Integer id, PaymentStatus status, Order order) {
        this.id = id;
        this.status = status;
        this.order = order;
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
        Payment other = (Payment) obj;
        if (id == null) {
            if (other.id != null)
                return false;
        } else if (!id.equals(other.id))
            return false;
        return true;
    }
}
