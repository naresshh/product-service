package com.ecom.product.mapper;

import com.ecom.product.dto.CategoryDto;
import com.ecom.product.dto.ProductDto;
import com.ecom.product.entity.Category;
import com.ecom.product.entity.Product;
import org.springframework.beans.BeanUtils;

public interface ProductMapper {

    public static Product map(ProductDto productDto){
        Category category = new Category();
        category.setCategoryId(productDto.getCategoryDto().getCategoryId());

        // Handle subcategory mapping
        Category subcategory = new Category();
        subcategory.setCategoryId(productDto.getCategoryDto().getCategoryId()); // Assuming you have a subcategory DTO

        Product product = new Product();
        product.setCategory(category);
        product.setSubcategory(subcategory);

        // Copy other properties
        BeanUtils.copyProperties(productDto, product);
        return product;
    }

    public static ProductDto map(Product product){
        CategoryDto categoryDto = new CategoryDto();
        categoryDto.setCategoryId(product.getCategory().getCategoryId());
        categoryDto.setCategoryTitle(product.getCategory().getCategoryTitle());

        // Handle subcategory mapping
        CategoryDto subcategoryDto = new CategoryDto();
        subcategoryDto.setCategoryId(product.getSubcategory().getCategoryId());
        subcategoryDto.setCategoryTitle(product.getSubcategory().getCategoryTitle());

        ProductDto productDto = new ProductDto();
        productDto.setCategoryDto(categoryDto);
        productDto.setSubcategoryDto(subcategoryDto); // Set subcategory DTO
        BeanUtils.copyProperties(product, productDto);
        return productDto;
    }
}