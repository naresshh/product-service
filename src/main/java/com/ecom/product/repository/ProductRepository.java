package com.ecom.product.repository;

import com.ecom.product.entity.Category;
import com.ecom.product.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Integer> {

    //List<Product> findByCategory(Category category);
    Product findByProductTitle(String productTitle);

    List<Product> findByCategory_CategoryId(Integer categoryId);

    List<Product> findByCategoryCategoryId(Integer categoryId);

    List<Product> findByCategoryCategoryTitle(String categoryTitle);

    List<Product> findByCategoryParentCategoryCategoryId(Integer subCategoryId);

    List<Product> findByCategory_CategoryIdAndCategory_CategoryTitle(Integer parentCategoryId, String subCategoryName);

    @Query("SELECT p FROM Product p WHERE p.category.categoryId = :categoryId AND p.subcategory.categoryId = :subcategoryId")
    List<Product> findByCategoryAndSubcategory(@Param("categoryId") Integer categoryId, @Param("subcategoryId") Integer subcategoryId);

}