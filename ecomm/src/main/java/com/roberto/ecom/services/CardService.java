package com.roberto.ecom.services;

import com.roberto.ecom.domain.Order;
import com.roberto.ecom.domain.PaymentCard;
import com.roberto.ecom.domain.enums.PaymentStatus;
import com.roberto.ecom.dto.PaymentCardDTO;

import org.springframework.stereotype.Service;

@Service
public class CardService {

    public PaymentCard createCard(PaymentCardDTO paymentDTO, Order order) {
        PaymentCard payment = new PaymentCard(null
            , PaymentStatus.PENDING
            , order
            , paymentDTO.getNumberOfInstallments());
        return payment;
    }
}
