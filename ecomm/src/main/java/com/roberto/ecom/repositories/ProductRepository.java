package com.roberto.ecom.repositories;

import java.util.List;

import com.roberto.ecom.domain.Category;
import com.roberto.ecom.domain.Product;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface ProductRepository extends JpaRepository<Product, Integer> {

    @Query(value = "select distinct p from Product p join p.categories cat" 
                 + " where p.name like %:name% and cat in :categories")
    @Transactional(readOnly = true)
    Page<Product> search(@Param("name") String productName, 
                         @Param("categories") List<Category> categories,
                         Pageable pageable);

    //same result as the method search
    @Transactional(readOnly = true)
    Page<Product> findDistinctByNameContainingAndCategoriesIn(
        String productName, List<Category> categories, Pageable pageable);

}
