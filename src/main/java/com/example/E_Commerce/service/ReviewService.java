package com.example.E_Commerce.service;

import com.example.E_Commerce.DTO.ReviewRequest;
import com.example.E_Commerce.exception.ProductException;
import com.example.E_Commerce.model.Review;
import com.example.E_Commerce.model.User;

import java.util.List;

public interface ReviewService {

    Review createReview(ReviewRequest request, User user) throws ProductException;
    List<Review> getAllReview(Long productId);
}
