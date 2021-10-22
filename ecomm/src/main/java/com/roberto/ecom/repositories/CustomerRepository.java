package com.roberto.ecom.repositories;

import com.roberto.ecom.domain.Customer;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, String> {

    @Transactional(readOnly = true)
    Customer findByEmail(String email);
}
