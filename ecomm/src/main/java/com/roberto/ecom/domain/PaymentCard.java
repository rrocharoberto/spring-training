package com.roberto.ecom.domain;

import javax.persistence.Entity;

import com.roberto.ecom.domain.enums.PaymentStatus;

import lombok.Getter;
import lombok.Setter;

@Entity
@Setter
@Getter
public class PaymentCard extends Payment {

    private Integer numberOfInstallments;

    public PaymentCard() {
    }

    public PaymentCard(Integer id, PaymentStatus status, Order order, Integer numberOfInstallments) {
        super(id, status, order);
        this.numberOfInstallments = numberOfInstallments;
    }
}