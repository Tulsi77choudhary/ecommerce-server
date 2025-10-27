package com.example.E_Commerce.repo;

import com.example.E_Commerce.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    @Query("""
SELECT p FROM Product p
WHERE (:category IS NULL OR :category = '' OR p.category.name = :category)
  AND (:minPrice IS NULL OR :maxPrice IS NULL OR p.discountPrice BETWEEN :minPrice AND :maxPrice)
  AND (:minDiscount IS NULL OR p.discountPresent >= :minDiscount)
""")
    List<Product> filterProducts(
            @Param("category") String category,
            @Param("minPrice") Integer minPrice,
            @Param("maxPrice") Integer maxPrice,
            @Param("minDiscount") Integer minDiscount
    );

}
