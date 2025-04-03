package com.ecom.product.dto;


import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Set;

public class CategoryDto {

    private Integer categoryId;
    private String categoryTitle;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Set<CategoryDto> subCategoriesDto;

    @JsonProperty("parentCategory")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private CategoryDto parentCategoryDto;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Set<ProductDto> productDto;

    public Integer getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Integer categoryId) {
        this.categoryId = categoryId;
    }

    public String getCategoryTitle() {
        return categoryTitle;
    }

    public void setCategoryTitle(String categoryTitle) {
        this.categoryTitle = categoryTitle;
    }

    public Set<CategoryDto> getSubCategoriesDto() {
        return subCategoriesDto;
    }

    public void setSubCategoriesDto(Set<CategoryDto> subCategoriesDto) {
        this.subCategoriesDto = subCategoriesDto;
    }

    public CategoryDto getParentCategoryDto() {
        return parentCategoryDto;
    }

    public void setParentCategoryDto(CategoryDto parentCategoryDto) {
        this.parentCategoryDto = parentCategoryDto;
    }

    public Set<ProductDto> getProductDto() {
        return productDto;
    }

    public void setProductDto(Set<ProductDto> productDto) {
        this.productDto = productDto;
    }
}