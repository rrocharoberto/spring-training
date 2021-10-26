package com.roberto.ecom.services;

import java.util.Random;

import com.roberto.ecom.domain.Customer;
import com.roberto.ecom.repositories.CustomerRepository;
import com.roberto.ecom.services.exceptions.ObjectNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthenticatorService {

    @Autowired
    private CustomerRepository customerRepo;

    @Autowired
    private BCryptPasswordEncoder passEncoder;

    @Autowired
    private MockEmailService emailService;

    private Random rand = new Random();

    public void sendNewPassword(String email) {
        Customer customer = customerRepo.findByEmail(email);
        if (customer == null) {
            throw new ObjectNotFoundException("Email not found.");
        }

        String newPass = generateNewPassword();
        customer.setPassword(passEncoder.encode(newPass));

        customerRepo.save(customer);

        emailService.sendNewPassword(customer, newPass);
    }

    private String generateNewPassword() {
        StringBuilder password = new StringBuilder();
        for (int i = 0; i < 10; i++) {
            password.append(getChar());
        }
        return password.toString();
    }

    private Character getChar() {
        int type = rand.nextInt(3);
        if (type == 0) {
            return (char) (rand.nextInt(26) + 65); // capital letters
        } else if (type == 1) {
            return (char) (rand.nextInt(26) + 97); // small letters
        }
        return (char) (rand.nextInt(10) + 48); // numbers
    }
}