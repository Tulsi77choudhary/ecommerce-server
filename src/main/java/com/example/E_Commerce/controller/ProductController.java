package com.example.E_Commerce.controller;

import com.example.E_Commerce.DTO.CreateProductRequest;
import com.example.E_Commerce.exception.ProductException;
import com.example.E_Commerce.model.Product;
import com.example.E_Commerce.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/product")
@CrossOrigin(origins = "http://localhost:5173")
public class ProductController {
    @Autowired
    private ProductService productService;

    @PostMapping("/products")
    public ResponseEntity<Product> createProduct(@RequestBody CreateProductRequest request) {
        Product product = productService.createProduct(request);
        return ResponseEntity.ok(product);
    }
    @GetMapping("/products")
    public ResponseEntity<Page<Product>> findProductByCategoryHandler(
            @RequestParam(required = false, defaultValue = "") String category,
            @RequestParam(required = false) List<String> color,
            @RequestParam(required = false) List<String> size,
            @RequestParam(required = false, defaultValue = "0") Integer minPrice,
            @RequestParam(required = false, defaultValue = "1000000") Integer maxPrice,
            @RequestParam(required = false, defaultValue = "0") Integer minDiscount,
            @RequestParam(required = false, defaultValue = "price_low") String sort,
            @RequestParam(required = false, defaultValue = "all") String stock,
            @RequestParam(required = false, defaultValue = "0") Integer pageNumber,
            @RequestParam(required = false, defaultValue = "1") Integer pageSize
    ) {
        Page<Product> res = productService.getAllProduct(
                category, color, size,
                minPrice, maxPrice, minDiscount,
                sort, stock, pageNumber, pageSize
        );
        return new ResponseEntity<>(res, HttpStatus.OK);
    }


    @GetMapping("/products/id/{productId}")
    public  ResponseEntity<Product> findProductByIdHandler(@PathVariable Long productId) throws ProductException{
        Product product = productService.findProductById(productId);
        return new ResponseEntity<>(product,HttpStatus.ACCEPTED);
    }

}
