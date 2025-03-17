package com.ecom.product.mapper;

import com.ecom.product.dto.ProductDTO;
import com.ecom.product.entity.Product;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ProductMapper {
    @Mapping(source = "id", target = "id")
    ProductDTO toDto(Product product);

    @Mapping(target = "id", ignore = true)
    Product toEntity(ProductDTO dto);
}