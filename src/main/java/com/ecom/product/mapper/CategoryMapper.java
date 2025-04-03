package com.ecom.product.mapper;

import com.ecom.product.dto.CategoryDto;
import com.ecom.product.entity.Category;
import org.springframework.beans.BeanUtils;

import java.util.HashSet;
import java.util.Set;

public interface CategoryMapper {

    public static Category map(CategoryDto categoryDto){
        if(categoryDto == null){
            return null;
        }

        Category category = new Category();
        BeanUtils.copyProperties(categoryDto, category);

        // Handle the parent category mapping if exists
        if(category.getParentCategory() != null){
            Category parentCategory = new Category();
            parentCategory.setCategoryId(category.getParentCategory().getCategoryId());
            category.setParentCategory(parentCategory);
        } else {
            category.setParentCategory(null);
        }

        // Handle the subcategories if any
        if (categoryDto.getSubCategoriesDto() != null && !categoryDto.getSubCategoriesDto().isEmpty()) {
            Set<Category> subCategories = new HashSet<>();
            for (CategoryDto subCategoryDto : categoryDto.getSubCategoriesDto()) {
                Category subCategory = new Category();
                BeanUtils.copyProperties(subCategoryDto, subCategory);
                subCategories.add(subCategory);
            }
            category.setSubCategories(subCategories);
        }

        return category;
    }

    public static CategoryDto map(Category category){
        if(category == null){
            return null;
        }

        CategoryDto categoryDto = new CategoryDto();
        BeanUtils.copyProperties(category, categoryDto);

        // Handle the parent category mapping if exists
        if(category.getParentCategory() != null){
            CategoryDto parentCategoryDto = new CategoryDto();
            BeanUtils.copyProperties(category.getParentCategory(), parentCategoryDto);
            categoryDto.setParentCategoryDto(parentCategoryDto);
        }

        // Handle the subcategories if any
        if (category.getSubCategories() != null && !category.getSubCategories().isEmpty()) {
            Set<CategoryDto> subCategoryDtos = new HashSet<>();
            for (Category subCategory : category.getSubCategories()) {
                CategoryDto subCategoryDto = new CategoryDto();
                BeanUtils.copyProperties(subCategory, subCategoryDto);
                subCategoryDtos.add(subCategoryDto);
            }
            categoryDto.setSubCategoriesDto(subCategoryDtos);
        }

        return categoryDto;
    }
}