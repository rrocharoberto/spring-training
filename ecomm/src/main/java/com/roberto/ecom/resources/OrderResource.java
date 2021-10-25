package com.roberto.ecom.resources;

import java.net.URI;
import java.util.List;

import javax.validation.Valid;

import com.roberto.ecom.domain.Order;
import com.roberto.ecom.dto.OrderDTO;
import com.roberto.ecom.services.OrderService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@RestController
@RequestMapping("/orders")
public class OrderResource {

    @Autowired
    private OrderService service;

    @GetMapping("")
    @PreAuthorize("hasAnyRole('ADMIN')")
    public List<OrderDTO> listAll() {
        return service.findAll();
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<?> find(@PathVariable Integer id) {
        OrderDTO order = service.findById(id);
        return ResponseEntity.ok().body(order);
    }

    @PostMapping("")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<Void> createOrder(@Valid @RequestBody OrderDTO orderDTO) {
        Order order = service.createOrder(orderDTO);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(order.getId()).toUri();
        return ResponseEntity.created(uri).build();
    }

    @GetMapping("/search")
    public ResponseEntity<Page<OrderDTO>> findAllPage(
        @RequestParam(value="pageNumber", defaultValue = "0") Integer pageNumber, 
        @RequestParam(value="linesPerPage", defaultValue = "24") Integer linesPerPage, 
        @RequestParam(value="orderBy", defaultValue = "dateCreated") String orderBy,
        @RequestParam(value="direction", defaultValue = "DESC") String direction) {

        Page<OrderDTO> page = service
            .findPage(pageNumber, linesPerPage, orderBy, direction)
            .map(c -> new OrderDTO(c));
        return ResponseEntity.ok().body(page);
    }
}