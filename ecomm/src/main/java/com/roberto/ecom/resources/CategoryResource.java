package com.roberto.ecom.resources;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;

import com.roberto.ecom.domain.Category;
import com.roberto.ecom.dto.CategoryDTO;
import com.roberto.ecom.services.CategoryService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
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
@RequestMapping("/categories")
public class CategoryResource {

    @Autowired
    private CategoryService service;

    @GetMapping("")
    public ResponseEntity<List<CategoryDTO>> findAll() {
        List<CategoryDTO> list = service.findAll()
            .stream()
            .map(c -> new CategoryDTO(c))
            .collect(Collectors.toList());
        return ResponseEntity.ok().body(list);
    }

    @GetMapping("/page")
    public ResponseEntity<Page<CategoryDTO>> findAllPage(
        @RequestParam(value="pageNumber", defaultValue = "0") Integer pageNumber, 
        @RequestParam(value="linesPerPage", defaultValue = "24") Integer linesPerPage, 
        @RequestParam(value="orderBy", defaultValue = "name") String orderBy,
        @RequestParam(value="direction", defaultValue = "ASC") String direction) {

        Page<CategoryDTO> page = service
            .findPage(pageNumber, linesPerPage, orderBy, direction)
            .map(c -> new CategoryDTO(c));
        return ResponseEntity.ok().body(page);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CategoryDTO> find(@PathVariable Integer id) {
        Category cat = service.findById(id);
        return ResponseEntity.ok().body(new CategoryDTO(cat));
    }

    @PostMapping("")
    @PreAuthorize("hasAnyRole('ADMIN')")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<Void> createCategory(@Valid @RequestBody CategoryDTO category) {
        service.createCategory(service.toCategory(category));
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(category.getId()).toUri();
        return ResponseEntity.created(uri).build();
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN')")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateCategory(@Valid @RequestBody CategoryDTO category, @PathVariable Integer id) {
        service.updateCategory(service.toCategory(category), id);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN')")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteCategory(@PathVariable Integer id) {
        service.deleteCategory(id);
    }
}
