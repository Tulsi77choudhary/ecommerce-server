package com.example.E_Commerce.service;

import com.example.E_Commerce.exception.CartItemException;
import com.example.E_Commerce.exception.UserException;
import com.example.E_Commerce.model.Cart;
import com.example.E_Commerce.model.CartItem;
import com.example.E_Commerce.model.Product;

public interface CartItemService {

    CartItem createCartItem(CartItem cartItem);

    CartItem updateCartItem(Long userId,Long id,CartItem cartItem) throws CartItemException, UserException;

    CartItem isCartItemExist(Cart cart, Product product, String size, Long userId);

    void removeCartItem(Long userId,Long cartItemId) throws CartItemException,UserException;

    CartItem findCartItemById(Long cartItemId) throws CartItemException;
}
