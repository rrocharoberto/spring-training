package com.roberto.ecom.services;

import java.util.List;
import java.util.Optional;

import com.roberto.ecom.domain.Address;
import com.roberto.ecom.domain.City;
import com.roberto.ecom.domain.Customer;
import com.roberto.ecom.domain.enums.CustomerType;
import com.roberto.ecom.dto.CustomerDTO;
import com.roberto.ecom.dto.CustomerNewDTO;
import com.roberto.ecom.repositories.AddressRepository;
import com.roberto.ecom.repositories.CityRepository;
import com.roberto.ecom.repositories.CustomerRepository;
import com.roberto.ecom.services.exceptions.ECommerceException;
import com.roberto.ecom.services.exceptions.ObjectNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class CustomerService {

    @Autowired
    private CustomerRepository repo;

    @Autowired
    private CityRepository cityRepo;

    @Autowired
    private AddressRepository addressRepo;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    public List<Customer> findAll() {
        return repo.findAll();
    }

    public Customer findById(String id) {
        Optional<Customer> obj = repo.findById(id);
        return obj.orElseThrow(() -> new ObjectNotFoundException("Customer " + id + " not found."));
    }

    public void createCustomer(Customer customer) {
        Optional<Customer> existingCustomer = repo.findById(customer.getId());
        if (existingCustomer.isPresent()) {
            throw new ECommerceException("Customer already exist with this id: " + customer.getId());
        }
        checkDuplicateEmail(customer.getEmail());

        repo.save(customer);
        addressRepo.saveAll(customer.getAddresses());
    }

    public void updateCustomer(Customer customer, String id) {
        if (id == null || customer == null || !id.equals(customer.getId())) {
            throw new ECommerceException("Invalid customer id.");
        }
       
        Customer existingSameEmail = repo.findByEmail(customer.getEmail());
        if (existingSameEmail != null && !id.equals(existingSameEmail.getId())) {
            throw new ECommerceException("Customer already exist with this email: " + customer.getEmail());
        }
        Customer existingObj = findById(id);
        
        updateData(existingObj, customer);
        repo.save(customer);
    }

    public void deleteCustomer(String id) {
        if (id == null) {
            throw new ECommerceException("Customer id can not be null.");
        }
        Customer cat = findById(id);
        try {
            repo.delete(cat);
        } catch (DataIntegrityViolationException e) {
            throw new ECommerceException("Can not delete a customer with products.");
        }
    }

    public Page<Customer> findPage(Integer page, Integer linesPerPage, String orderBy, String direction) {
        // PageRequest pageRequest = PageRequest.of(page, linesPerPage,
        // Sort.by(orderBy));
        PageRequest pageRequest = PageRequest.of(page, linesPerPage, Direction.valueOf(direction), orderBy);
        return repo.findAll(pageRequest);
    }

    private void checkDuplicateEmail(String email) {
        Customer existingSameEmail = repo.findByEmail(email);
        if (existingSameEmail != null) {
            throw new ECommerceException("Customer already exist with this email: " + email);
        }
    }

    public Customer toCustomer(CustomerDTO customer) {
        return new Customer(customer.getId(), customer.getName(), customer.getEmail(),
                CustomerType.toEnum(customer.getType()), null);
    }

    public Customer toCustomer(CustomerNewDTO customer) {
        Customer customerEntity = new Customer(customer.getId(), customer.getName(), customer.getEmail(),
                CustomerType.toEnum(customer.getType()), passwordEncoder.encode(customer.getPassword()));
        City city = cityRepo.findById(customer.getCityId())
                .orElseThrow(() -> new ObjectNotFoundException("City not found: " + customer.getCityId()));

        Address addr = new Address(null, customer.getStreet(), customer.getNumber(), customer.getExtra(),
                customer.getNeighbour(), customer.getZipCode(), customerEntity, city);
        customerEntity.addAddress(addr);

        customer.getPhones().forEach(p -> customerEntity.addPhone(p.getNumber()));
        return customerEntity;
    }

    private void updateData(Customer existingObj, Customer customer) {
        existingObj.setName(customer.getName());
    }

}
