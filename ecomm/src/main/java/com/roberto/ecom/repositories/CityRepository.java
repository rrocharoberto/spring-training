package com.roberto.ecom.repositories;

import com.roberto.ecom.domain.City;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CityRepository extends JpaRepository<City, Integer> {
    
}
