package com.roberto.ecom.config;

import com.roberto.ecom.services.EmailService;
import com.roberto.ecom.services.MockEmailService;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
@Profile("test")
public class TestConfig {
    
    @Bean
    public EmailService emailService() {
        return new MockEmailService();
    }
}