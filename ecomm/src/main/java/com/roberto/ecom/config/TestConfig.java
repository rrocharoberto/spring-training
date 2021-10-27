package com.roberto.ecom.config;

import com.roberto.ecom.services.DBService;
import com.roberto.ecom.services.EmailService;
import com.roberto.ecom.services.MockEmailService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
@Profile("test")
public class TestConfig {
    
    @Autowired
    private DBService dbService;

    @Bean
    public EmailService emailService() {
        return new MockEmailService();
    }

    @Bean
    public boolean initiateTestDatabase() {
        dbService.initiateTestDatabase();
        return true;
    }
}