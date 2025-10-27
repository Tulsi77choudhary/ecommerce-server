package com.example.E_Commerce.repo;

import com.example.E_Commerce.model.Cart;
import com.example.E_Commerce.model.CartItem;
import com.example.E_Commerce.model.Product;
import com.example.E_Commerce.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface CartItemRepository extends JpaRepository<CartItem,Long> {

    @Query("SELECT ci FROM CartItem ci WHERE ci.cart = :cart AND ci.product = :product AND ci.size = :size AND ci.user = :user")
    CartItem isCartItemExist(
            @Param("cart") Cart cart,
            @Param("product") Product product,
            @Param("size") String size,
            @Param("user") User user
    );

}
