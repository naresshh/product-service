package com.ecom.product.controller;

import com.ecom.product.dto.ProductDto;
import com.ecom.product.entity.Product;
import com.ecom.product.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("api/products")
@CrossOrigin(origins = "http://localhost:5173")
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @PostMapping
    public ResponseEntity<ProductDto> createProduct(@RequestBody ProductDto productDto) {
        return ResponseEntity.ok(productService.createProduct(productDto));
    }

    @PutMapping("/{productId}")
    public ResponseEntity<ProductDto> updateProduct(@PathVariable Integer productId, @RequestBody ProductDto productDto) {
        return ResponseEntity.ok(productService.update(productId, productDto));
    }

        @GetMapping("/{productId}")
        public ResponseEntity<ProductDto> getProductById(@PathVariable Integer productId) {
            return ResponseEntity.ok(productService.findById(productId));
        }

    @GetMapping
    public ResponseEntity<List<ProductDto>> getAllProducts() {
        return ResponseEntity.ok(productService.findAll());
    }

    @DeleteMapping("/{productId}")
    public ResponseEntity<Void> deleteProduct(@PathVariable Integer productId) {
        productService.deleteById(productId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/products")
    public List<ProductDto> getProducts(
            @RequestParam Integer categoryId,
            @RequestParam Integer subcategoryId
    ) {
        return productService.getProductsByCategoryAndSubcategory(categoryId, subcategoryId);
    }

}