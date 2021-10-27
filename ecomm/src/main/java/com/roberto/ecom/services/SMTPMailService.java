package com.roberto.ecom.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;

import lombok.extern.java.Log;

@Log
public class SMTPMailService extends AbstractEmailService {

    @Autowired
    private MailSender mailSender;

    @Override
    public void sendMail(SimpleMailMessage message) {
        log.info("Sending the email...");
        mailSender.send(message);
        log.info("E-mail sent!");
    }
}