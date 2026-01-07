package com.example.E_Commerce.controller;

import com.example.E_Commerce.exception.OrderException;
import com.example.E_Commerce.exception.UserException;
import com.example.E_Commerce.model.Address;
import com.example.E_Commerce.model.Order;
import com.example.E_Commerce.model.User;
import com.example.E_Commerce.service.OrderService;
import com.example.E_Commerce.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.util.List;

@RestController
@RequestMapping("api/orders")
@CrossOrigin(origins = "http://localhost:5173")
public class OrderController {
    @Autowired
    private OrderService orderService;
    @Autowired
    private UserService userService;

    @PostMapping("/order")
    public ResponseEntity<Order> createOrder(@RequestBody Address shippingAddress,
                                             @RequestHeader("Authorization") String jwt) throws UserException, OrderException {
        User user  = userService.findUserProfileByJwt(jwt);

        Order order = orderService.createOrder(user, shippingAddress);

        System.out.println("order" + order);
        return new ResponseEntity<>(order, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Order> findOrderById(
            @PathVariable("id") Long orderId,
            @RequestHeader("Authorization") String jwt) throws UserException,OrderException {

        User user = userService.findUserProfileByJwt(jwt);
        Order order = orderService.findOrderById(orderId);
        return new ResponseEntity<>(order,HttpStatus.OK);
    }

    @GetMapping("/user")
    public ResponseEntity<List<Order>> userOrderHistory(@RequestHeader("Authorization") String jwt) throws UserException, OrderException {

        User user = userService.findUserProfileByJwt(jwt);
        List<Order> orders = orderService.usersOrderHistory(user.getId());
        return new ResponseEntity<>(orders,HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> DeleteOrder(@PathVariable("id") Long orderId,@RequestHeader("Authorization") String jwt) throws UserException, OrderException {
        User user = userService.findUserProfileByJwt(jwt);
        orderService.deleteOrderById(orderId);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body("Order deleted successfully with ID: " + orderId);
    }

}
