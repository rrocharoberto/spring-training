package com.roberto.ecom.services;

import com.roberto.ecom.dto.OrderDTO;

import org.springframework.mail.SimpleMailMessage;

public interface EmailService {

    void sendOrderConfirmationMail(OrderDTO order);

    void sendMail(SimpleMailMessage message);
}