package com.roberto.ecom.resources;

import java.util.Arrays;
import java.util.List;

import com.roberto.ecom.domain.Category;
import com.roberto.ecom.services.CategoryService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/categories")
public class CategoryResource {

    @Autowired
    private CategoryService service;

    @GetMapping("/hello")
    public String hello() {
        return "Hello from Category resource.";
    }

    @GetMapping("")
    public List<Category> listAll() {
        // Category cat1 = new Category(1, "Cat1");
        // Category cat2 = new Category(2, "Cat2");
        // return Arrays.asList(cat1, cat2);
        return service.findAll();
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<?> find(@PathVariable Integer id) {
        Category cat = service.findById(id);
        // if(cat == null) {
        //    return ResponseEntity.notFound().build();
        // }
        return ResponseEntity.ok().body(cat);
    }
}
