package com.ecom.product.dto;

import jakarta.validation.constraints.NotBlank;

public class CategoryDTO {

    private Long id;
    @NotBlank(message = "Category name must not be blank")
    private String name;
    // Constructors
    public CategoryDTO() {}
    public CategoryDTO(Long id, String name) {
        this.id = id;
        this.name = name;
    }
    // Getters and setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
}