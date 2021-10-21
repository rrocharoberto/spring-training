package com.roberto.ecom.services;

import java.util.List;
import java.util.Optional;

import com.roberto.ecom.domain.Category;
import com.roberto.ecom.dto.CategoryDTO;
import com.roberto.ecom.repositories.CategoryRepository;
import com.roberto.ecom.services.exceptions.ECommerceException;
import com.roberto.ecom.services.exceptions.ObjectNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
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

    public void createCategory(Category category) {
        repo.save(category);
    }

    public void updateCategory(Category category, Integer id) {
        if (id == null || category == null || id.equals(category.getId())) {
            throw new ECommerceException("Invalid category id.");
        }
        Category existingObj = findById(id);
        updateData(existingObj, category);
        repo.save(category);
    }

    public void deleteCategory(Integer id) {
        if (id == null) {
            throw new ECommerceException("Category id can not be null.");
        }
        Category cat = findById(id);
        try {
            repo.delete(cat);
        } catch (DataIntegrityViolationException e) {
            throw new ECommerceException("Can not delete a category with products.");
        }
    }

    public Page<Category> findPage(Integer page, Integer linesPerPage, String orderBy, String direction) {
        // PageRequest pageRequest = PageRequest.of(page, linesPerPage,
        // Sort.by(orderBy));
        PageRequest pageRequest = PageRequest.of(page, linesPerPage, Direction.valueOf(direction), orderBy);
        return repo.findAll(pageRequest);
    }

    public Category toCategory(CategoryDTO category) {
        return new Category(category.getId(), category.getName());
    }

    private void updateData(Category existingObj, Category category) {
        existingObj.setName(category.getName());
    }

}
