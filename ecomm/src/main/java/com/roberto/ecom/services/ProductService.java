package com.roberto.ecom.services;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import com.roberto.ecom.domain.Category;
import com.roberto.ecom.domain.Product;
import com.roberto.ecom.dto.ProductDTO;
import com.roberto.ecom.repositories.CategoryRepository;
import com.roberto.ecom.repositories.ProductRepository;
import com.roberto.ecom.services.exceptions.ObjectNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

@Service
public class ProductService {

    @Autowired
    private ProductRepository repo;

    @Autowired
    private CategoryRepository categoryRepo;

    public List<ProductDTO> findAll() {
        return repo.findAll().stream()
            .map(p -> new ProductDTO(p))
            .collect(Collectors.toList());
    }

    public ProductDTO findById(Integer id) {
        Optional<Product> obj = repo.findById(id);
        Product product = obj.orElseThrow(() -> new ObjectNotFoundException("Product " + id + " not found."));
        return new ProductDTO(product);
    }

    public Page<Product> searchBy(String name, List<Integer> categoryIds, Integer page, Integer linesPerPage,
            String orderBy, String direction) {
        Pageable pageable = PageRequest.of(page, linesPerPage, Direction.valueOf(direction), orderBy);

        List<Category> categories = categoryRepo.findAllById(categoryIds);
        return repo.findDistinctByNameContainingAndCategoriesIn(name, categories, pageable);
    }
}
