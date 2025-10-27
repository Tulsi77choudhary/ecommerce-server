package com.example.E_Commerce.repo;

import com.example.E_Commerce.model.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface ReviewRepository extends JpaRepository<Review,Long> {
    @Query("Select r from Review r where r.product.id=:productId")
    List<Review> getAllProductsReview(
            @Param("productId")Long productId
    );
}
