package com.example.E_Commerce.service;

import com.example.E_Commerce.DTO.ReviewRequest;
import com.example.E_Commerce.exception.ProductException;
import com.example.E_Commerce.model.Product;
import com.example.E_Commerce.model.Review;
import com.example.E_Commerce.model.User;
import com.example.E_Commerce.repo.ProductRepository;
import com.example.E_Commerce.repo.ReviewRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class ReviewServiceImpl implements ReviewService{

    @Autowired
    private ReviewRepository reviewRepository;
    @Autowired
    private ProductService productService;
    @Autowired
    private ProductRepository productRepository;
    @Override
    public Review createReview(ReviewRequest request, User user) throws ProductException {
        Product product = productService.findProductById(request.getProductId());

        Review review = new Review();
        review.setUser(user);
        review.setProduct(product);
        review.setReview(request.getReview());
        review.setCreateAt(LocalDateTime.now());

        return reviewRepository.save(review);
    }

    @Override
    public List<Review> getAllReview(Long productId) {
        return  reviewRepository.getAllProductsReview(productId);
    }
}
