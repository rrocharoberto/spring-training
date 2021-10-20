package com.roberto.ecom.resources;

import java.util.Arrays;
import java.util.List;

import com.roberto.ecom.domain.Customer;
import com.roberto.ecom.services.CustomerService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/customers")
public class CustomerResource {

    @Autowired
    private CustomerService service;

    @GetMapping("")
    public List<Customer> listAll() {
        return service.findAll();
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<?> find(@PathVariable String id) {
        Customer customer = service.findById(id);
        return ResponseEntity.ok().body(customer);
    }
}
