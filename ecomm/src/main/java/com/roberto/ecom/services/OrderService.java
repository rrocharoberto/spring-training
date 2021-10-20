package com.roberto.ecom.services;

import java.util.List;
import java.util.Optional;

import com.roberto.ecom.domain.Order;
import com.roberto.ecom.repositories.OrderRepository;
import com.roberto.ecom.services.exceptions.ObjectNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OrderService {

    @Autowired
    private OrderRepository repo;

    public List<Order> findAll() {
        return repo.findAll();
    }

    public Order findById(Integer id) {
        Optional<Order> obj = repo.findById(id);
        return obj.orElseThrow(() -> new ObjectNotFoundException("Order " + id + " not found."));
    }
}
