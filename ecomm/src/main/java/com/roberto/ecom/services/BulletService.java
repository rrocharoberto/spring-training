package com.roberto.ecom.services;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

import com.roberto.ecom.domain.Order;
import com.roberto.ecom.domain.PaymentBullet;
import com.roberto.ecom.domain.enums.PaymentStatus;
import com.roberto.ecom.dto.PaymentBulletDTO;

import org.springframework.stereotype.Service;

@Service
public class BulletService {

    public PaymentBullet createBullet(PaymentBulletDTO paymentDTO, Order order) {
        PaymentBullet payment = new PaymentBullet(null, PaymentStatus.PENDING, order, null,
                this.getDueDate(order.getDateCreated()));
        return payment;
    }

    private LocalDate getDueDate(LocalDateTime baseDate) {
        return baseDate.plus(7, ChronoUnit.DAYS).toLocalDate();
    }
}
