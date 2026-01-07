package com.example.E_Commerce.controller;

import com.example.E_Commerce.DTO.ReviewRequest;
import com.example.E_Commerce.exception.ProductException;
import com.example.E_Commerce.exception.UserException;
import com.example.E_Commerce.model.Review;
import com.example.E_Commerce.model.User;
import com.example.E_Commerce.service.ReviewService;
import com.example.E_Commerce.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/review")
public class ReviewController {

    @Autowired
    private ReviewService reviewService;

    @Autowired
    private UserService userService;

    @PostMapping("create")
    public ResponseEntity<Review> createReviewReview(@RequestBody ReviewRequest request, @RequestHeader("Authorization") String jwt) throws UserException, ProductException {
        User user = userService.findUserProfileByJwt(jwt);
        Review review = reviewService.createReview(request,user);
        return new ResponseEntity<>(review, HttpStatus.CREATED);
    }

    @GetMapping("/product/{productId}")
    public ResponseEntity<List<Review>> getProductReview(@PathVariable Long productId) throws UserException,ProductException{
        List<Review> reviews = reviewService.getAllReview(productId);
        return new ResponseEntity<>(reviews,HttpStatus.CREATED);
    }
}
