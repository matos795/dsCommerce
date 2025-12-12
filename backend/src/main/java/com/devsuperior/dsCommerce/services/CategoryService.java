package com.devsuperior.dsCommerce.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.devsuperior.dsCommerce.dto.CategoryDTO;
import com.devsuperior.dsCommerce.entities.Category;
import com.devsuperior.dsCommerce.repositories.CategoryRepository;

@Service
public class CategoryService {

    @Autowired
    private CategoryRepository repository;

    @Transactional(readOnly = true)
    public List<CategoryDTO> findAll() {
        List<Category> obj = repository.findAll();
        return obj.stream().map(x -> new CategoryDTO(x)).toList();
    }

}
