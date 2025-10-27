package com.example.E_Commerce.service;

import com.example.E_Commerce.exception.OrderException;
import com.example.E_Commerce.model.Address;
import com.example.E_Commerce.model.Order;
import com.example.E_Commerce.model.User;
import java.util.List;

public interface OrderService  {

    Order createOrder(User user, Address shippingAddress) throws OrderException;
    Order findOrderById(Long orderId) throws OrderException;
    List<Order> usersOrderHistory(Long userId) throws OrderException;

    Order placedOrder(Long orderId) throws OrderException;
    Order confirmedOrder(Long orderId) throws OrderException;

    Order shippedOrder(Long orderId) throws OrderException;
    Order deliveredOrder(Long orderId) throws OrderException;
    Order cancelOrder(Long userId) throws  OrderException;
    List<Order> getAllOrders();

    void deleteOrderById(Long orderId) throws OrderException;


}
