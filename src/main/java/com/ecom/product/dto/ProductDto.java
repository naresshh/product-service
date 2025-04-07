package com.ecom.product.dto;

import com.ecom.product.entity.Category;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

public class ProductDto {

    private Integer productId;
    private String productTitle;
    private String sku;
    private Double priceUnit;
    private Integer quanity;


    @JsonInclude(JsonInclude.Include.NON_NULL)
    private CategoryDto category;

    private CategoryDto subcategoryDto;

    public Integer getProductId() {
        return productId;
    }

    public void setProductId(Integer productId) {
        this.productId = productId;
    }

    public String getProductTitle() {
        return productTitle;
    }

    public void setProductTitle(String productTitle) {
        this.productTitle = productTitle;
    }

    public String getSku() {
        return sku;
    }

    public void setSku(String sku) {
        this.sku = sku;
    }

    public Double getPriceUnit() {
        return priceUnit;
    }

    public void setPriceUnit(Double priceUnit) {
        this.priceUnit = priceUnit;
    }

    public Integer getQuanity() {
        return quanity;
    }

    public void setQuanity(Integer quanity) {
        this.quanity = quanity;
    }

    public CategoryDto getCategory() {
        return category;
    }

    public void setCategory(CategoryDto category) {
        this.category = category;
    }

    public CategoryDto getSubcategoryDto() {
        return subcategoryDto;
    }

    public void setSubcategoryDto(CategoryDto subcategoryDto) {
        this.subcategoryDto = subcategoryDto;
    }
}