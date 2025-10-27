package com.example.E_Commerce.service;

import com.example.E_Commerce.DTO.AddItemRequest;
import com.example.E_Commerce.exception.ProductException;
import com.example.E_Commerce.model.Cart;
import com.example.E_Commerce.model.CartItem;
import com.example.E_Commerce.model.Product;
import com.example.E_Commerce.model.User;
import com.example.E_Commerce.repo.CartRepository;
import com.example.E_Commerce.repo.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
public class CartServiceImpl implements CartService{

    @Autowired
    private CartRepository cartRepository;
    @Autowired
    private CartItemService cartItemService;
    @Autowired
    private ProductService productService;
    @Autowired
    private UserRepository userRepository;


    @Override
    public Cart createCart(User user) {
        Cart cart = new Cart();
        cart.setUser(user);
        return cartRepository.save(cart);
    }

    @Override
    public String addCartItem(Long userId, AddItemRequest request) throws ProductException {
        Cart cart = cartRepository.findByUserId(userId);
        Product product = productService.findProductById(request.getProductId());

        CartItem isPresent = cartItemService.isCartItemExist(cart, product, request.getSize(), userId);
        if (isPresent == null) {
            CartItem cartItem = new CartItem();
            cartItem.setProduct(product);
            cartItem.setCart(cart);
            cartItem.setQuantity(request.getQuantity());
            cartItem.setSize(request.getSize());

            User user = userRepository.findById(userId)
                    .orElseThrow(() -> new ProductException("User not found"));
            cartItem.setUser(user);

            int price = request.getPrice() * product.getDiscountPrice();
            cartItem.setPrice(price);

            CartItem createdCartItem = cartItemService.createCartItem(cartItem);

            cart.getCartItems().add(createdCartItem);
        }

        return "Item Added To Cart";
    }

    @Transactional
    @Override
    public Cart findUserCart(Long userId) {
        Cart cart = cartRepository.findByUserId(userId);

        if (cart == null) {
            return null; // or throw exception
        }

        // Make a safe copy of cart items to avoid ConcurrentModificationException
        Set<CartItem> itemsCopy = new HashSet<>(cart.getCartItems());

        int totalPrice = 0;
        int totalDiscountedPrice = 0;
        int totalItem = 0;

        for (CartItem cartItem : itemsCopy) {
            totalPrice += cartItem.getPrice();
            totalDiscountedPrice += cartItem.getDiscountedPrice();
            totalItem += cartItem.getQuantity();
        }

        cart.setTotalDiscountedPrice(totalDiscountedPrice);
        cart.setTotalItem(totalItem);
        cart.setTotalPrice(totalPrice);
        cart.setDiscount(totalPrice - totalDiscountedPrice);

        return cartRepository.save(cart);
    }


}
