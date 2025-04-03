package com.ecom.product.service;

import com.ecom.product.dto.ProductDto;
import com.ecom.product.entity.Category;
import com.ecom.product.entity.Product;
import com.ecom.product.exception.ProductNotFoundException;
import com.ecom.product.mapper.ProductMapper;
import com.ecom.product.repository.CategoryRepository;
import com.ecom.product.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductService {
    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;

    public ProductService(ProductRepository productRepository, CategoryRepository categoryRepository) {
        this.productRepository = productRepository;
        this.categoryRepository = categoryRepository;
    }

    public ProductDto createProduct(ProductDto productDto) {
        Category category = categoryRepository.findById(productDto.getCategoryDto().getCategoryId())
                .orElseThrow(() -> new RuntimeException("Category not found"));

        Category subcategory = null;
        if (productDto.getSubcategoryDto() != null) {
            subcategory = categoryRepository.findById(productDto.getSubcategoryDto().getCategoryId())
                    .orElseThrow(() -> new RuntimeException("Subcategory not found"));
        }

        Product product = ProductMapper.map(productDto);
        product.setCategory(category);
        product.setSubcategory(subcategory);
        product = productRepository.save(product);
        return ProductMapper.map(product);
    }

    public ProductDto update(Integer productId, ProductDto productDto) {
        Product product = productRepository.findById(productId).orElseThrow(() -> new RuntimeException("Product not found"));
        product.setProductTitle(productDto.getProductTitle());
        product.setSku(productDto.getSku());
        product.setPriceUnit(productDto.getPriceUnit());
        product.setQuanity(productDto.getQuanity());
        return ProductMapper.map(productRepository.save(product));
    }

    public List<ProductDto> findAll() {
        return productRepository.findAll().stream().map(ProductMapper::map).collect(Collectors.toList());
    }

    public ProductDto findById(Integer productId) {
        return productRepository.findById(productId).map(ProductMapper::map)
                .orElseThrow(() -> new ProductNotFoundException("Product with ID " + productId + " not found"));
    }

    public void deleteById(Integer productId) {
        productRepository.deleteById(productId);
    }

    public ProductDto findByName(String productTitle) {
        return ProductMapper.map(productRepository.findByProductTitle(productTitle));
    }

    public List<ProductDto> findByCategoryId(Integer categoryId) {
        return productRepository.findByCategoryCategoryId(categoryId).stream().map(ProductMapper::map).collect(Collectors.toList());
    }

    public List<ProductDto> findByCategoryName(String categoryTitle) {
        return productRepository.findByCategoryCategoryTitle(categoryTitle).stream().map(ProductMapper::map).collect(Collectors.toList());
    }

    public List<ProductDto> findBySubCategory(Integer subCategoryId) {
        return productRepository.findByCategoryCategoryId(subCategoryId)
                .stream().map(ProductMapper::map).collect(Collectors.toList());
    }

    public List<ProductDto> findProductsByCategoryAndSubCategory(Integer parentCategoryId, String subCategoryName) {
        // First, find the parent category
        Category parentCategory = categoryRepository.findById(parentCategoryId)
                .orElseThrow(() -> new RuntimeException("Parent Category not found"));

        // Find the subcategory using the name
        Category subCategory = parentCategory.getSubCategories().stream()
                .filter(cat -> cat.getCategoryTitle().equalsIgnoreCase(subCategoryName))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Subcategory not found"));

        // Now, fetch the products that belong to the parent category and subcategory
        return productRepository.findByCategory_CategoryIdAndCategory_CategoryTitle(parentCategoryId, subCategory.getCategoryTitle())
                .stream().map(ProductMapper::map).collect(Collectors.toList());
    }


    // Fetch products by category (subcategory) ID
    public List<Product> getProductsBySubCategoryId(Integer subCategoryId) {
        return productRepository.findByCategory_CategoryId(subCategoryId);
    }

    public List<ProductDto> getProductsByCategoryAndSubcategory(Integer categoryId, Integer subcategoryId) {
         List<Product> products = productRepository.findByCategoryAndSubcategory(categoryId, subcategoryId);
         List<ProductDto> productDtos = products.stream().map(ProductMapper::map).collect(Collectors.toList());
         return  productDtos;
    }
}