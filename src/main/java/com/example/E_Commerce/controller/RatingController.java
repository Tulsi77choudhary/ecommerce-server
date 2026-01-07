package com.example.E_Commerce.controller;

import com.example.E_Commerce.DTO.RatingRequest;
import com.example.E_Commerce.exception.ProductException;
import com.example.E_Commerce.exception.UserException;
import com.example.E_Commerce.model.Rating;
import com.example.E_Commerce.model.User;
import com.example.E_Commerce.service.RatingService;
import com.example.E_Commerce.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/rating")
public class RatingController {
    @Autowired
    private RatingService ratingService;
    @Autowired
    private UserService userService;

    @PostMapping("/create")
    public ResponseEntity<Rating> createRating(@RequestBody RatingRequest request, @RequestHeader("Authorization") String jwt) throws UserException , ProductException {
        User user = userService.findUserProfileByJwt(jwt);
        Rating rating = ratingService.createRating(request,user);
        return new ResponseEntity<Rating>(rating, HttpStatus.CREATED);
    }

    @GetMapping("/product/{productId}")
    public ResponseEntity<List<Rating>> getProductRating(@PathVariable Long productId,@RequestHeader("Authorization") String jwt) throws UserException,ProductException{
        User user = userService.findUserProfileByJwt(jwt);
        List<Rating> ratings = ratingService.getProductsRating(productId);
        return new ResponseEntity<>(ratings,HttpStatus.CREATED);
    }
}
