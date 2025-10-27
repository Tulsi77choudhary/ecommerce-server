package com.example.E_Commerce.service;

import com.example.E_Commerce.model.OrderItem;
import com.example.E_Commerce.repo.OrderItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OrderItemServiceImpl implements OrderItemService{
    @Autowired
    private OrderItemRepository orderItemRepository;
    @Override
    public OrderItem createOrderItemById(OrderItem orderItem) {
        return orderItemRepository.save(orderItem);
    }


}
