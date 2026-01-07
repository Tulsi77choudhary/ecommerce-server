package com.example.E_Commerce.controller;

import com.example.E_Commerce.DTO.ApiResponse;
import com.example.E_Commerce.exception.OrderException;
import com.example.E_Commerce.model.Order;
import com.example.E_Commerce.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin/orders")
public class AdminOrderController {
    @Autowired
    private OrderService orderService;

    @GetMapping("/")
    public ResponseEntity<List<Order>> getAllOrderHandler(){
        List<Order> orders = orderService.getAllOrders();
        return new ResponseEntity<List<Order>>(orders, HttpStatus.ACCEPTED);
    }
    @PutMapping("/{orderId}/confirmed")
    public ResponseEntity<Order> ConfirmedOrderHandler(
            @PathVariable Long orderId, @RequestHeader("Authorization") String jwt) throws OrderException{
        Order order = orderService.confirmedOrder(orderId);
        return new ResponseEntity<>(order,HttpStatus.OK);
    }

    @PutMapping("/{orderId}/ship")
    public ResponseEntity<Order> ShippedOrderHandler(@PathVariable Long orderId,
                                                     @RequestHeader("Authorization") String jwt) throws OrderException{
        Order order = orderService.shippedOrder(orderId);
        return new ResponseEntity<>(order,HttpStatus.OK);
    }

    @PutMapping("/{orderId}/deliver")
    public ResponseEntity<Order> DeliverOrderHandler(@PathVariable Long orderId,
                                                     @RequestHeader("Authorization") String jwt) throws OrderException{
        Order order = orderService.deliveredOrder(orderId);
        return new ResponseEntity<>(order,HttpStatus.OK);
    }

    @PutMapping("/{orderId}/cancel")
    public ResponseEntity<Order> CancelOrderHandler(@PathVariable Long orderId, @RequestHeader("Authorization") String jwt) throws OrderException{

        Order order = orderService.cancelOrder(orderId);
        return new ResponseEntity<>(order,HttpStatus.OK);
    }

    @DeleteMapping("/{orderId}/delete")
    public ResponseEntity<Order> DeleteOrderHandler(@PathVariable Long orderId,
                                                    @RequestHeader("Authorization") String jwt) throws OrderException{
        orderService.deleteOrderById(orderId);
        ApiResponse response = new ApiResponse();
        response.setMessage("order deleted successfully");
        response.setStatus(true);
        return new ResponseEntity<Order>(HttpStatus.OK);
    }
}
