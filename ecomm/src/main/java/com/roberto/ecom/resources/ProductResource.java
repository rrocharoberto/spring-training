package com.roberto.ecom.resources;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import com.roberto.ecom.domain.Product;
import com.roberto.ecom.dto.ProductDTO;
import com.roberto.ecom.services.ProductService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/products")
public class ProductResource {

    @Autowired
    private ProductService service;

    @GetMapping("")
    public List<ProductDTO> listAll() {
        return service.findAll();
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<ProductDTO> find(@PathVariable Integer id) {
        Product product = service.findById(id);
        return ResponseEntity.ok().body(new ProductDTO(product));
    }

    @GetMapping("/search")
    public ResponseEntity<Page<ProductDTO>> findAllPage(
        @RequestParam(value="productName", defaultValue = "") String productName, 
        @RequestParam(value="categories", defaultValue = "") String categories, //categories separated by comma (,)
        @RequestParam(value="pageNumber", defaultValue = "0") Integer pageNumber, 
        @RequestParam(value="linesPerPage", defaultValue = "24") Integer linesPerPage, 
        @RequestParam(value="orderBy", defaultValue = "name") String orderBy,
        @RequestParam(value="direction", defaultValue = "ASC") String direction) {

        List<Integer> categoryIds = new ArrayList<>();
        categoryIds.addAll(Arrays.asList(categories.split(",")).stream().map(c -> Integer.parseInt(c)).collect(Collectors.toList()));

        String productNameDecoded;
        try {
            productNameDecoded = URLDecoder.decode(productName, StandardCharsets.UTF_8.name());
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            productNameDecoded = "";
        }

        Page<ProductDTO> page = service
            .searchBy(productNameDecoded, categoryIds, pageNumber, linesPerPage, orderBy, direction)
            .map(c -> new ProductDTO(c));
        return ResponseEntity.ok().body(page);
    }
}
