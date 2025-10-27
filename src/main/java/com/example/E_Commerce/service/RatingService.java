package com.example.E_Commerce.service;

import com.example.E_Commerce.DTO.RatingRequest;
import com.example.E_Commerce.exception.ProductException;
import com.example.E_Commerce.model.Rating;
import com.example.E_Commerce.model.User;

import java.util.List;

public interface RatingService {

    Rating createRating(RatingRequest request, User user) throws ProductException;

    List<Rating> getProductsRating(Long productId);
}
