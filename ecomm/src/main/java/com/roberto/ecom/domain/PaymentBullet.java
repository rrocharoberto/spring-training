package com.roberto.ecom.domain;

import java.time.LocalDate;
import java.time.OffsetDateTime;

import javax.persistence.Entity;

import com.roberto.ecom.domain.enums.PaymentStatus;

import lombok.Getter;
import lombok.Setter;

@Entity
@Setter
@Getter
public class PaymentBullet extends Payment {

    private OffsetDateTime paymentDate;

    private LocalDate dueDate;

    public PaymentBullet() {
    }

    public PaymentBullet(Integer id, PaymentStatus status, Order order, OffsetDateTime paymentDate, LocalDate dueDate) {
        super(id, status, order);
        this.paymentDate = paymentDate;
        this.dueDate = dueDate;
    }
}