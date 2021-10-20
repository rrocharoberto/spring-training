package com.roberto.ecom.domain;

import java.time.LocalDate;
import java.time.OffsetDateTime;

import javax.persistence.Entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.roberto.ecom.domain.enums.PaymentStatus;

import lombok.Getter;
import lombok.Setter;

@Entity
public class PaymentBullet extends Payment {
    
    @JsonFormat(pattern = "dd/MM/yyyy")
    @Setter
    @Getter
    private OffsetDateTime paymentDate;
    
    @JsonFormat(pattern = "dd/MM/yyyy")
    @Setter
    @Getter
    private LocalDate dueDate;

    public PaymentBullet() {
    }

    public PaymentBullet(Integer id, PaymentStatus status, Order order, OffsetDateTime paymentDate,
            LocalDate dueDate) {
        super(id, status, order);
        this.paymentDate = paymentDate;
        this.dueDate = dueDate;
    }

}
