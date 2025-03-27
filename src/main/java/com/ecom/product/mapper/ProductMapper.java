package com.ecom.product.mapper;

import com.ecom.product.dto.ProductDTO;
import com.ecom.product.entity.Product;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ProductMapper {
    @Mapping(source = "id", target = "id")
    @Mapping(source = "category.id", target = "categoryId")
    @Mapping(source = "unit", target = "unit")
    ProductDTO toDto(Product product);

    @Mapping(target = "id", ignore = true)
    @Mapping(source = "unit", target = "unit")
    Product toEntity(ProductDTO dto);
}