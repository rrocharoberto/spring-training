package com.roberto.ecom.resources;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;

import com.roberto.ecom.domain.Customer;
import com.roberto.ecom.dto.CustomerDTO;
import com.roberto.ecom.dto.CustomerNewDTO;
import com.roberto.ecom.services.CustomerService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@RestController
@RequestMapping("/customers")
public class CustomerResource {

    @Autowired
    private CustomerService service;

    @GetMapping("")
    public ResponseEntity<List<CustomerDTO>> findAll() {
        List<CustomerDTO> list = service.findAll()
            .stream()
            .map(c -> new CustomerDTO(c))
            .collect(Collectors.toList());
        return ResponseEntity.ok().body(list);
    }

    @GetMapping("/page")
    public ResponseEntity<Page<CustomerDTO>> findAllPage(
        @RequestParam(value="pageNumber", defaultValue = "0") Integer pageNumber, 
        @RequestParam(value="linesPerPage", defaultValue = "24") Integer linesPerPage, 
        @RequestParam(value="orderBy", defaultValue = "name") String orderBy,
        @RequestParam(value="direction", defaultValue = "ASC") String direction) {

        Page<CustomerDTO> page = service
            .findPage(pageNumber, linesPerPage, orderBy, direction)
            .map(c -> new CustomerDTO(c));
        return ResponseEntity.ok().body(page);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CustomerDTO> find(@PathVariable String id) {
        Customer customer = service.findById(id);
        return ResponseEntity.ok().body(new CustomerDTO(customer));
    }

    @PostMapping("")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<Void> createCustomer(@Valid @RequestBody CustomerNewDTO customer) {
        service.createCustomer(service.toCustomer(customer));
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(customer.getId()).toUri();
        return ResponseEntity.created(uri).build();
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateCustomer(@Valid @RequestBody CustomerDTO customer, @PathVariable String id) {
        service.updateCustomer(service.toCustomer(customer), id);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteCustomer(@PathVariable String id) {
        service.deleteCustomer(id);
    }

}
