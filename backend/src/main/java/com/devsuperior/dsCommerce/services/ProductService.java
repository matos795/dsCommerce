package com.devsuperior.dsCommerce.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.devsuperior.dsCommerce.dto.CategoryDTO;
import com.devsuperior.dsCommerce.dto.ProductDTO;
import com.devsuperior.dsCommerce.dto.ProductMinDTO;
import com.devsuperior.dsCommerce.entities.Category;
import com.devsuperior.dsCommerce.entities.Product;
import com.devsuperior.dsCommerce.repositories.ProductRepository;
import com.devsuperior.dsCommerce.services.exceptions.DatabaseException;
import com.devsuperior.dsCommerce.services.exceptions.ResourceNotFoundException;

@Service
public class ProductService {

    @Autowired
    private ProductRepository repository;

    @Transactional(readOnly = true)
    public ProductDTO findById(Long id) {
        Product product = repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Recurso não encontrado!"));
        return new ProductDTO(product);
    }

    @Transactional(readOnly = true)
    public Page<ProductMinDTO> searchAllByName(String name, Pageable pageable) {
        Page<Product> obj = repository.searchAllByName(name, pageable);
        return obj.map(x -> new ProductMinDTO(x));
    }

    @Transactional
    public ProductDTO insert(ProductDTO dto) {
        Product entity = new Product();
        copyDTOToEntity(dto, entity);
        entity = repository.save(entity);
        return new ProductDTO(entity);
}

    @Transactional
    public ProductDTO update(Long id, ProductDTO dto) {
        Product entity = repository.getReferenceById(id);
        copyDTOToEntity(dto, entity);
        entity = repository.save(entity);
        return new ProductDTO(entity);
    }

    @Transactional(propagation = Propagation.SUPPORTS)
public void delete(Long id) {
	if (!repository.existsById(id)) {
		throw new ResourceNotFoundException("Recurso não encontrado");
	}
	try {
        	repository.deleteById(id);    		
	}
    	catch (DataIntegrityViolationException e) {
        	throw new DatabaseException("Falha de integridade referencial");
   	}
}


    private void copyDTOToEntity(ProductDTO dto, Product entity) {
        entity.setName(dto.getName());
        entity.setDescription(dto.getDescription());
        entity.setPrice(dto.getPrice());
        entity.setImgUrl(dto.getImgUrl());
        entity.getCategories().clear();
        for (CategoryDTO catDTO : dto.getCategories()) {
            Category cat = new Category();
            cat.setId(catDTO.getId());
            entity.getCategories().add(cat);
        }
    }
}
