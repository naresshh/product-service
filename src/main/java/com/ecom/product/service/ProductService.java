package com.ecom.product.service;

import com.ecom.product.dto.ProductDTO;
import com.ecom.product.entity.Category;
import com.ecom.product.entity.Product;
import com.ecom.product.exception.ProductNotFoundException;
import com.ecom.product.mapper.ProductMapper;
import com.ecom.product.repository.CategoryRepository;
import com.ecom.product.repository.ProductRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductService {

    private final ProductRepository repository;
    private final CategoryRepository categoryRepository;
    private final ProductMapper productMapper;

    public ProductService(ProductRepository repository, ProductMapper productMapper,CategoryRepository categoryRepository) {
        this.repository = repository;
        this.productMapper = productMapper;
        this.categoryRepository=categoryRepository;
    }

    public ProductDTO createProduct(ProductDTO dto) {
        if (dto == null) {
            throw new IllegalArgumentException("ProductDTO must not be null");
        }
        Category category = categoryRepository.findById(dto.getCategoryId()) // assuming getCategoryId() is available in ProductDTO
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Category not found"));

       Product product = productMapper.toEntity(dto);
       product.setCategory(category);

       Product savedProduct = repository.save(product);

        return productMapper.toDto(savedProduct);
    }



    public ProductDTO getProduct(Long id) {
        return repository.findById(id)
                .map(productMapper::toDto)
                .orElseThrow(() -> new ProductNotFoundException("Product with ID " + id + " not found"));
    }

    public List<ProductDTO> getProductsByCategoryId(Long categoryId) {
        try {
            Category category = categoryRepository.findById(categoryId)
                    .orElseThrow(() -> new ProductNotFoundException("No Products found"));

            List<Product> products = repository.findByCategory(category);

            return products.stream()
                    .map(product -> productMapper.toDto(product))
                    .collect(Collectors.toList());

        } catch (ProductNotFoundException e) {
            throw new ProductNotFoundException("No Products");
        }
    }

    public List<ProductDTO> getProducts(){
        List<Product> products = repository.findAll();
        return products.stream()
                .map(product -> productMapper.toDto(product))
                .collect(Collectors.toList());
    }

}