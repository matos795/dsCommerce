package com.devsuperior.dsCommerce.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.devsuperior.dsCommerce.entities.OrderItem;
import com.devsuperior.dsCommerce.pk.OrderItemPK;

public interface OrderItemRepository extends JpaRepository<OrderItem, OrderItemPK>{

}
