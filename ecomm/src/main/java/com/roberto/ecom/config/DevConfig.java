package com.roberto.ecom.config;

import com.roberto.ecom.services.DBService;
import com.roberto.ecom.services.EmailService;
import com.roberto.ecom.services.SMTPMailService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
@Profile("dev")
public class DevConfig {

    @Value("${spring.jpa.hibernate.ddl-auto}")
    private String dbStrategy;

    @Autowired
    private DBService dbService;

    @Bean
    public EmailService emailService() {
        return new SMTPMailService();
    }

    @Bean
    public boolean initiateTestDatabase() {
        if ("create".equals(dbStrategy)) {
            dbService.initiateTestDatabase();
        }
        return true;
    }
}