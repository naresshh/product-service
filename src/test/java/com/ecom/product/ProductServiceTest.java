package com.ecom.product;

import com.ecom.product.dto.ProductDTO;
import com.ecom.product.entity.Product;
import com.ecom.product.exception.ProductNotFoundException;
import com.ecom.product.mapper.ProductMapper;
import com.ecom.product.repository.ProductRepository;
import com.ecom.product.service.ProductService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class ProductServiceTest {

    @Mock
    private ProductRepository repository;

    @Mock
    private ProductMapper mapper;

    @InjectMocks
    private ProductService service;

    private Product product;
    private ProductDTO productDTO;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        // Setup Product Entity
        product = new Product();
        product.setId(1L);
        product.setName("iPhone 16");
        product.setPrice(999.99);

        // Setup Product DTO
        productDTO = new ProductDTO();
        productDTO.setId(1L);
        productDTO.setName("iPhone 16");
        productDTO.setPrice(999.99);
    }

    @Test
    void createProduct_shouldReturnProductDTO_whenSavedSuccessfully() {

        // Arrange
        Product productWithoutId = new Product();
        productWithoutId.setName(productDTO.getName());
        productWithoutId.setPrice(productDTO.getPrice());

        when(mapper.toEntity(productDTO)).thenReturn(productWithoutId);
        when(repository.save(productWithoutId)).thenReturn(product);
        when(mapper.toDto(product)).thenReturn(productDTO);

        // Act
        ProductDTO result = service.createProduct(productDTO);

        // Assert
        assertNotNull(result);
        assertEquals(1L, result.getId());
        assertEquals("iPhone 16", result.getName());
        assertEquals(999.99, result.getPrice());

        verify(mapper).toEntity(productDTO);
        verify(repository).save(productWithoutId);
        verify(mapper).toDto(product);

    }

    @Test
    void getProduct_shouldReturnProductDTO_whenProductExists() {
        // Arrange
        when(repository.findById(1L)).thenReturn(Optional.of(product));
        when(mapper.toDto(product)).thenReturn(productDTO);

        // Act
        ProductDTO result = service.getProduct(1L);

        // Assert
        assertNotNull(result);
        assertEquals(1L, result.getId());
        assertEquals("iPhone 16", result.getName());

        verify(repository).findById(1L);
        verify(mapper).toDto(product);
    }

    // ================== NEGATIVE TEST CASES ==================

    @Test
    void getProduct_shouldThrowProductNotFoundException_whenProductDoesNotExist() {
        // Arrange
        when(repository.findById(99L)).thenReturn(Optional.empty());

        // Act + Assert
        ProductNotFoundException exception = assertThrows(ProductNotFoundException.class, () -> {
            service.getProduct(99L);
        });

        assertEquals("Product with ID 99 not found", exception.getMessage());

        verify(repository).findById(99L);
        verify(mapper, never()).toDto(any());
    }

    @Test
    void createProduct_shouldThrowNullPointerException_whenInputIsNull() {
        // Act + Assert
        assertThrows(IllegalArgumentException.class, () -> {
            service.createProduct(null);
        });

        verify(mapper, never()).toEntity(any());
        verify(repository, never()).save(any());
    }

    @Test
    void createProduct_shouldThrowException_whenRepositorySaveFails() {
        // Arrange
        Product productWithoutId = new Product();
        productWithoutId.setName(productDTO.getName());
        productWithoutId.setPrice(productDTO.getPrice());

        when(mapper.toEntity(productDTO)).thenReturn(productWithoutId);
        when(repository.save(productWithoutId)).thenThrow(new RuntimeException("Database error"));

        // Act + Assert
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            service.createProduct(productDTO);
        });

        assertEquals("Database error", exception.getMessage());

        verify(mapper).toEntity(productDTO);
        verify(repository).save(productWithoutId);
    }
}