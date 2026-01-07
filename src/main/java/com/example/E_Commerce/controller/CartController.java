package com.example.E_Commerce.controller;

import com.example.E_Commerce.DTO.AddItemRequest;
import com.example.E_Commerce.exception.CartItemException;
import com.example.E_Commerce.exception.ProductException;
import com.example.E_Commerce.exception.UserException;
import com.example.E_Commerce.model.Cart;
import com.example.E_Commerce.model.CartItem;
import com.example.E_Commerce.model.User;
import com.example.E_Commerce.service.CartItemService;
import com.example.E_Commerce.service.CartService;
import com.example.E_Commerce.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/cart")
@CrossOrigin(origins = "http://localhost:5173")
public class CartController {

    @Autowired
    private CartService cartService;
    @Autowired
    private CartItemService cartItemService;
    @PostMapping("/create/{userId}")
    public ResponseEntity<Cart> createCart(@PathVariable Long userId) {
        User user = new User();
        user.setId(userId);
        Cart cart = cartService.createCart(user);
        return new ResponseEntity<>(cart, HttpStatus.CREATED);
    }
    @PostMapping("/{userId}/add")
    public ResponseEntity<String> addItemToCart(
            @PathVariable Long userId,
            @RequestBody AddItemRequest request) throws ProductException {

        String result = cartService.addCartItem(userId, request);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @GetMapping("/{userId}")
    public ResponseEntity<Cart> getUserCart(@PathVariable Long userId) throws ProductException {
        Cart cart = cartService.findUserCart(userId);
        return new ResponseEntity<>(cart, HttpStatus.OK);
    }

    @DeleteMapping("/{userId}/{cartItemId}")
    public ResponseEntity<String> removeCartItem(
            @PathVariable Long userId,
            @PathVariable Long cartItemId) {

        try {
            cartItemService.removeCartItem(userId, cartItemId);
            return ResponseEntity.ok("Cart item removed successfully");
        }
        catch (UserException | CartItemException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
        catch (Exception e) {
            return ResponseEntity.internalServerError().body("Something went wrong: " + e.getMessage());
        }
    }

    @PutMapping("/update/{cartItemId}/{userId}")
    public  ResponseEntity<String> updateCartItem(@PathVariable Long cartItemId, @PathVariable Long userId, @RequestBody Map<String,Integer> request){
        try{
            int newQuantity = request.get("quantity");
            CartItem cartItem = new CartItem();
            cartItem.setQuantity(newQuantity);
            cartItemService.updateCartItem(userId,cartItemId,cartItem);
            return ResponseEntity.ok("Cart item update successfully");

        } catch (CartItemException | UserException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Something went wrong: " + e.getMessage());
        }
    }

}
