package com.example.E_Commerce.controller;

import com.example.E_Commerce.DTO.ApiResponse;
import com.example.E_Commerce.DTO.CreateProductRequest;
import com.example.E_Commerce.exception.OrderException;
import com.example.E_Commerce.exception.ProductException;
import com.example.E_Commerce.model.Order;
import com.example.E_Commerce.model.Product;
import com.example.E_Commerce.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin/products")
public class AdminProductController {
    @Autowired
    private ProductService productService;

    @PostMapping("/")
    public ResponseEntity<Product> createProduct(@RequestBody CreateProductRequest request){
        Product product = productService.createProduct(request);
        return new ResponseEntity<Product>(product, HttpStatus.CREATED);
    }

    @DeleteMapping("/{productId}/delete")
    public ResponseEntity<ApiResponse> deleteProduct(@PathVariable Long productId) throws ProductException{
        productService.deleteProduct(productId);
        ApiResponse response = new ApiResponse();
        response.setMessage("product delete successfully");
        response.setStatus(true);
        return new ResponseEntity<>(response,HttpStatus.OK);
    }

    @GetMapping("/all")
    public ResponseEntity<List<Product>> findAllProduct(){
        List<Product> products = productService.findAllProducts();
        return new ResponseEntity<>(products,HttpStatus.OK);
    }

    @PutMapping("/{orderId}/confirmed")
    public ResponseEntity<Product> updateProducts(@RequestBody Product req,
            @PathVariable Long productId) throws OrderException, ProductException {
        Product product = productService.updateProduct(productId,req);
        return new ResponseEntity<>(product,HttpStatus.CREATED);
    }
}
