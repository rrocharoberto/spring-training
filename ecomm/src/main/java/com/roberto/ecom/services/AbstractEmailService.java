package com.roberto.ecom.services;

import java.util.Date;

import com.roberto.ecom.dto.OrderDTO;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;

public abstract class AbstractEmailService implements EmailService {
    
    @Value("${email.default.sender}")
    private String sender;

    @Override
    public void sendOrderConfirmationMail(OrderDTO order) {
        SimpleMailMessage smm = prepareSimpleMailMessageFromOrder(order);
        sendMail(smm);
    }

    protected SimpleMailMessage prepareSimpleMailMessageFromOrder(OrderDTO order) {
        SimpleMailMessage msg = new SimpleMailMessage();
        msg.setTo(order.getCustomerEmail());
        msg.setFrom(sender);
        msg.setSubject("Order confirmed " + order.getOrderId());
        msg.setSentDate(new Date());
        msg.setText(order.toString());
        return msg;
    }
}