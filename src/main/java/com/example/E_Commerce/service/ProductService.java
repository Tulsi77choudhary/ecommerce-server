package com.example.E_Commerce.service;

import com.example.E_Commerce.DTO.CreateProductRequest;
import com.example.E_Commerce.exception.ProductException;
import com.example.E_Commerce.model.Product;
import org.springframework.data.domain.Page;

import java.util.List;

public interface ProductService {
    Product createProduct(CreateProductRequest request);

    String deleteProduct(Long productId) throws ProductException;

    Product updateProduct(Long productId, Product req) throws ProductException;

    Product findProductById(Long id) throws ProductException;

    List<Product> findProductByCategory(String category);

    Page<Product> getAllProduct(String category,List<String>colors,List<String>sizes,Integer minPrice,Integer maxPrice,
                                Integer minDiscount, String sort,String stock, Integer pageNumber,Integer pageSize);

    List<Product> findAllProducts();
}
