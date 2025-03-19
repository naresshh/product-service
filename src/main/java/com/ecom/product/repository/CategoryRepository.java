package com.ecom.product.repository;

import com.ecom.product.dto.ProductDTO;
import com.ecom.product.entity.Category;
import com.ecom.product.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CategoryRepository extends JpaRepository<Category, Long> {
    Optional<Category> findByName(String name);
}