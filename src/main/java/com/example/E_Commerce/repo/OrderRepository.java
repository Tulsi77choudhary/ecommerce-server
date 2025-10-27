package com.example.E_Commerce.repo;

import com.example.E_Commerce.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order,Long> {

    @Query("SELECT o FROM Order o " +
            "WHERE o.user.id = :userId " +
            "AND o.orderStatus IN ('PLACED', 'CONFIRMED', 'SHIPPED', 'DELIVER')")
    List<Order> getUserOrders(@Param("userId") Long userId);


}
