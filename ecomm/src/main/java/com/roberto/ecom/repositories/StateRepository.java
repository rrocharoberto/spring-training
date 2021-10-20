package com.roberto.ecom.repositories;

import com.roberto.ecom.domain.State;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StateRepository extends JpaRepository<State, Integer> {
    
}
