package com.roberto.ecom.services;

import java.util.Date;

import com.roberto.ecom.domain.Customer;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Service;

import lombok.extern.java.Log;

@Service
@Log
public class MockEmailService {

    public void sendNewPassword(Customer customer, String newPassword) {
        SimpleMailMessage msg = new SimpleMailMessage();
        msg.setTo(customer.getEmail());
        msg.setFrom("sender@mock");
        msg.setSubject("New password request.");
        msg.setSentDate(new Date());
        msg.setText("Your new password: " + newPassword);
        sendEmail(msg);
    }

    public void sendEmail(SimpleMailMessage msg) {
        log.info("Simulating the email sending...");
        log.info(msg.toString());
        log.info("E-mail sent!");
    }
}
