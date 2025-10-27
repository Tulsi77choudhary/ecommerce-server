package com.example.E_Commerce.controller;

import com.example.E_Commerce.model.OrderItem;
import com.example.E_Commerce.service.OrderItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "http://localhost:5173")
@RequestMapping("api/order-items")
public class OrderItemController {

    @Autowired
    private OrderItemService orderItemService;

    @PostMapping("/")
    public ResponseEntity<OrderItem> createOrderItem(@RequestBody OrderItem orderItem){
        OrderItem createOrderItem = orderItemService.createOrderItemById(orderItem);
        return ResponseEntity.ok(createOrderItem);
    }

    @GetMapping("/{Id}")
    public ResponseEntity<OrderItem> getOrderItemById(@PathVariable("id") OrderItem orderItem){
        OrderItem order = orderItemService.createOrderItemById(orderItem);
        if (orderItem != null) {
            return ResponseEntity.ok(orderItem);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
