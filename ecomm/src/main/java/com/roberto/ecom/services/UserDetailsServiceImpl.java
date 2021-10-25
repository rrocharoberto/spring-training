package com.roberto.ecom.services;

import com.roberto.ecom.domain.Customer;
import com.roberto.ecom.repositories.CustomerRepository;
import com.roberto.ecom.security.EcomUserPrincipal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private CustomerRepository customerRepo;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Customer customer = customerRepo.findByEmail(email);
        if (customer == null) {
            throw new UsernameNotFoundException("User not found with email: " + email);
        }
        return new EcomUserPrincipal(customer.getId(), customer.getEmail(), customer.getPassword(),
                customer.getProfiles());
    }
}
