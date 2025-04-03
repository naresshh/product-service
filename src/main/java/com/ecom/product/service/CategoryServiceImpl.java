package com.ecom.product.service;

import com.ecom.product.dto.CategoryDto;
import com.ecom.product.entity.Category;
import com.ecom.product.mapper.CategoryMapper;
import com.ecom.product.repository.CategoryRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;

    public CategoryServiceImpl(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    public CategoryDto save(CategoryDto categoryDto) {
        Category category = CategoryMapper.map(categoryDto);
        if (categoryDto.getParentCategoryDto() != null) {
            Category parentCategory = categoryRepository.findById(categoryDto.getParentCategoryDto().getCategoryId())
                    .orElseThrow(() -> new RuntimeException("Parent category not found"));
            category.setParentCategory(parentCategory);
        }
        return CategoryMapper.map(categoryRepository.save(category));
    }

    public CategoryDto update(Integer categoryId, CategoryDto categoryDto) {
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new RuntimeException("Category not found"));
        category.setCategoryTitle(categoryDto.getCategoryTitle());
        return CategoryMapper.map(categoryRepository.save(category));
    }

    public List<CategoryDto> findAll() {
        return categoryRepository.findAll().stream()
                .map(CategoryMapper::map)
                .collect(Collectors.toList());
    }

    public CategoryDto findById(Integer categoryId) {
        return categoryRepository.findById(categoryId)
                .map(CategoryMapper::map)
                .orElseThrow(() -> new RuntimeException("Category not found"));
    }

    public List<CategoryDto> findSubCategoriesByCategoryName(String categoryName) {
        return categoryRepository.findByParentCategoryCategoryTitle(categoryName).stream()
                .map(CategoryMapper::map)
                .collect(Collectors.toList());
    }

    public void deleteCategory(Integer categoryId) {
        categoryRepository.deleteById(categoryId);
    }

    public List<CategoryDto> findAllCategories() {
        return categoryRepository.findAll().stream()
                .map(CategoryMapper::map)
                .collect(Collectors.toList());
    }

}