package com.roberto.ecom.resources;

import java.util.List;

import com.roberto.ecom.domain.Order;
import com.roberto.ecom.services.OrderService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/orders")
public class OrderResource {

    @Autowired
    private OrderService service;

    @GetMapping("")
    public List<Order> listAll() {
        return service.findAll();
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<?> find(@PathVariable Integer id) {
        Order cat = service.findById(id);
        return ResponseEntity.ok().body(cat);
    }
}
