package com.roberto.ecom.services;

import java.util.List;
import java.util.Optional;

import com.roberto.ecom.domain.Customer;
import com.roberto.ecom.repositories.CustomerRepository;
import com.roberto.ecom.services.exceptions.ObjectNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CustomerService {

    @Autowired
    private CustomerRepository repo;

    public List<Customer> findAll() {
        return repo.findAll();
    }

    public Customer findById(String id) {
        Optional<Customer> obj = repo.findById(id);
        return obj.orElseThrow(() -> new ObjectNotFoundException("Customer " + id + " not found."));
    }
}
