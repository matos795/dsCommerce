package com.devsuperior.dsCommerce.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.devsuperior.dsCommerce.entities.Order;

public interface OrderRepository extends JpaRepository<Order, Long>{

}
