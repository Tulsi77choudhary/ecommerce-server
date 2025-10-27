package com.example.E_Commerce.service;

import com.example.E_Commerce.DTO.AddItemRequest;
import com.example.E_Commerce.exception.ProductException;
import com.example.E_Commerce.model.Cart;
import com.example.E_Commerce.model.User;

public interface CartService {

    Cart createCart(User user);
    String addCartItem(Long userId, AddItemRequest request) throws ProductException;
    Cart findUserCart(Long userId);


}
