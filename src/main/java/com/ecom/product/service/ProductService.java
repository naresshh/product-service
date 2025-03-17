package com.ecom.product.service;

import com.ecom.product.dto.ProductDTO;
import com.ecom.product.entity.Product;
import com.ecom.product.exception.ProductNotFoundException;
import com.ecom.product.mapper.ProductMapper;
import com.ecom.product.repository.ProductRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class ProductService {

    private final ProductRepository repository;
    private final ProductMapper mapper;

    public ProductService(ProductRepository repository, ProductMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    public ProductDTO createProduct(ProductDTO dto) {
        if (dto == null) {
            throw new IllegalArgumentException("ProductDTO must not be null");
        }
        Product product = mapper.toEntity(dto);
        Product savedProduct = repository.save(product); // Save and get entity with generated ID
        ProductDTO productDTO =  mapper.toDto(savedProduct); // Convert to DTO with ID
        return  productDTO;
    }

    public ProductDTO getProduct(Long id) {
        return repository.findById(id)
                .map(mapper::toDto)
                .orElseThrow(() -> new ProductNotFoundException("Product with ID " + id + " not found"));
    }
}