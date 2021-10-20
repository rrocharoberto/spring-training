package com.roberto.ecom.services;

import java.util.List;
import java.util.Optional;

import com.roberto.ecom.domain.Category;
import com.roberto.ecom.repositories.CategoryRepository;
import com.roberto.ecom.services.exceptions.ObjectNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CategoryService {
    
    @Autowired
    private CategoryRepository repo;

    public List<Category> findAll() {
        return repo.findAll();
    }

    public Category findById(Integer id) {
        Optional<Category> obj = repo.findById(id);
        return obj.orElseThrow(() -> new ObjectNotFoundException("Category " + id + " not found."));
    }
}
