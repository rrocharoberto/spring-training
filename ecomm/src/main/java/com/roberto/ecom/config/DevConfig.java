package com.roberto.ecom.config;

import com.roberto.ecom.services.EmailService;
import com.roberto.ecom.services.SMTPMailService;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
@Profile("dev")
public class DevConfig {

    @Bean
    public EmailService emailService() {
        return new SMTPMailService();
    }
}