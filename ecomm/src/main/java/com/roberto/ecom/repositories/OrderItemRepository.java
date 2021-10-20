package com.roberto.ecom.repositories;

import com.roberto.ecom.domain.OrderItem;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderItemRepository extends JpaRepository<OrderItem, Integer> {

}
