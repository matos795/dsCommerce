package com.devsuperior.dsCommerce.controllers;


import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.devsuperior.dsCommerce.entities.Product;
import com.devsuperior.dsCommerce.repositories.ProductRepository;

@RestController
@RequestMapping(value = "/products")
public class ProductController {

    @Autowired
    private ProductRepository repository;
    @GetMapping
    public String teste(){

       Optional<Product> obj = repository.findById(1L);
       return obj.get().getName();
    }
}
