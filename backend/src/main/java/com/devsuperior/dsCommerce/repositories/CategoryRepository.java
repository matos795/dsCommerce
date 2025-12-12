package com.devsuperior.dsCommerce.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.devsuperior.dsCommerce.entities.Category;

public interface CategoryRepository extends JpaRepository<Category, Long> {

}
