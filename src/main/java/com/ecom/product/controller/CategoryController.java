package com.ecom.product.controller;

import com.ecom.product.dto.CategoryDto;
import com.ecom.product.entity.Category;
import com.ecom.product.mapper.CategoryMapper;
import com.ecom.product.repository.CategoryRepository;
import com.ecom.product.service.CategoryServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/categories")
@CrossOrigin(origins = "http://localhost:5173")
public class CategoryController {

    private final CategoryServiceImpl categoryService;

    @Autowired
    private CategoryRepository categoryRepository;

    public CategoryController(CategoryServiceImpl categoryService) {
        this.categoryService = categoryService;
    }

    @PostMapping
    public ResponseEntity<CategoryDto> createCategory(@RequestBody CategoryDto categoryDto) {
        return ResponseEntity.ok(categoryService.save(categoryDto));
    }

    @PostMapping("/{categoryId}/subcategories")
    public ResponseEntity<CategoryDto> createSubCategory(@PathVariable Integer categoryId, @RequestBody CategoryDto subCategoryDto) {
        Category parentCategory = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new RuntimeException("Parent category not found"));

        Category subCategory = CategoryMapper.map(subCategoryDto);
        subCategory.setParentCategory(parentCategory);
        subCategory = categoryRepository.save(subCategory);
        return ResponseEntity.ok(CategoryMapper.map(subCategory));
    }

    @PutMapping("/{categoryId}")
    public ResponseEntity<CategoryDto> updateCategory(@PathVariable Integer categoryId, @RequestBody CategoryDto categoryDto) {
        return ResponseEntity.ok(categoryService.update(categoryId, categoryDto));
    }


    @DeleteMapping("/{categoryId}")
    public ResponseEntity<Void> deleteCategory(@PathVariable Integer categoryId) {
        categoryService.deleteCategory(categoryId);
        return ResponseEntity.noContent().build();
    }
    @GetMapping
    public ResponseEntity<List<CategoryDto>> getAllCategories() {
        return ResponseEntity.ok(categoryService.findAllCategories());
    }

}