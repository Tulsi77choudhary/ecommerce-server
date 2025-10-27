package com.example.E_Commerce.service;

import com.example.E_Commerce.exception.CartItemException;
import com.example.E_Commerce.exception.UserException;
import com.example.E_Commerce.model.Cart;
import com.example.E_Commerce.model.CartItem;
import com.example.E_Commerce.model.Product;
import com.example.E_Commerce.model.User;
import com.example.E_Commerce.repo.CartItemRepository;

import com.example.E_Commerce.repo.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
@Service
@Transactional
public class CartItemServiceImpl implements CartItemService{
    @Autowired
    private CartItemRepository cartItemRepository;
    @Autowired
    private UserService userService;
    @Autowired
    private UserRepository userRepository;

    @Override
    public CartItem createCartItem(CartItem cartItem) {
        cartItem.setQuantity(1);
        cartItem.setPrice(cartItem.getProduct().getPrice() * cartItem.getQuantity());
        cartItem.setDiscountedPrice(cartItem.getProduct().getDiscountPrice() * cartItem.getQuantity());

        return cartItemRepository.save(cartItem);
    }

    @Override
    public CartItem updateCartItem(Long userId, Long id, CartItem cartItem)
            throws CartItemException, UserException {

        CartItem item = findCartItemById(id);
        User user = userService.findUserById(item.getUser().getId());

        if (user.getId().equals(userId)) {
            item.setQuantity(cartItem.getQuantity());
            item.setPrice(item.getQuantity() * item.getProduct().getPrice());
            item.setDiscountedPrice(item.getProduct().getDiscountPrice() * cartItem.getQuantity());
        }

        return cartItemRepository.save(item);
    }


    @Override
    public CartItem isCartItemExist(Cart cart, Product product, String size, Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found with id: " + userId));

        return cartItemRepository.isCartItemExist(cart, product, size, user);
    }
    @Override
    public void removeCartItem(Long userId, Long cartItemId) throws CartItemException, UserException {

        CartItem cartItem = findCartItemById(cartItemId);
        User user = cartItem.getUser();

        User reqUser = userService.findUserById(userId);

        if (user.getId().equals(reqUser.getId())){
            cartItemRepository.deleteById(cartItemId);
        }else {
            throw new UserException("You can't remove another users item");
        }
        cartItemRepository.delete(cartItem);

    }

    @Override
    public CartItem findCartItemById(Long cartItemId) throws CartItemException {
        Optional<CartItem> opt = cartItemRepository.findById(cartItemId);

        if (opt.isPresent()){
            return opt.get();
        }
        throw new CartItemException("cartItem not found with id :" + cartItemId);
    }
}
