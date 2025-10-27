package com.example.E_Commerce.service;

import com.example.E_Commerce.DTO.RatingRequest;
import com.example.E_Commerce.exception.ProductException;
import com.example.E_Commerce.model.Product;
import com.example.E_Commerce.model.Rating;
import com.example.E_Commerce.model.User;
import com.example.E_Commerce.repo.RatingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class RatingServiceImpl implements RatingService{
    @Autowired
    private RatingRepository ratingRepository;
    @Autowired
    private ProductService productService;
    @Override
    public Rating createRating(RatingRequest request, User user) throws ProductException {
        Product product = productService.findProductById(request.getProductId());

        Rating rating = new Rating();
        rating.setProduct(product);
        rating.setUser(user);
        rating.setRating(request.getRating());
        rating.setCreateAt(LocalDateTime.now());

        return ratingRepository.save(rating);
    }

    @Override
    public List<Rating> getProductsRating(Long productId) {
        return ratingRepository.getAllProductsRating(productId);
    }
}
