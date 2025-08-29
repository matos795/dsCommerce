package com.devsuperior.dsCommerce.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.devsuperior.dsCommerce.dto.ProductDTO;
import com.devsuperior.dsCommerce.entities.Product;
import com.devsuperior.dsCommerce.repositories.ProductRepository;

@Service
public class ProductService {

    @Autowired
    private ProductRepository repository;

    @Transactional(readOnly = true)
    public ProductDTO findById(Long id) {
        Optional<Product> obj = repository.findById(id);
        Product product = obj.get();
        return new ProductDTO(product);
    }
}
