package com.example.E_Commerce.service;

import com.example.E_Commerce.DTO.CreateProductRequest;
import com.example.E_Commerce.exception.ProductException;
import com.example.E_Commerce.model.Category;
import com.example.E_Commerce.model.Product;
import com.example.E_Commerce.repo.CategoryRepository;
import com.example.E_Commerce.repo.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ProductServiceImpl implements ProductService{
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private UserService userService;
    @Autowired
    private CategoryRepository categoryRepository;
    @Override
    public Product createProduct(CreateProductRequest request) {

        // Get or create top-level category
        List<Category> topCategories = categoryRepository.findByName(request.getTopLevelCategory());
        Category topLevel = topCategories.isEmpty() ? null : topCategories.get(0);

        if (topLevel == null) {
            topLevel = new Category();
            topLevel.setName(request.getTopLevelCategory());
            topLevel.setLevel(1);
            topLevel = categoryRepository.save(topLevel);
        }

        // Get or create second-level category
        List<Category> secondCategories = categoryRepository.findByNameAndParent(request.getSecondLevelCategory(), topLevel.getName());
        Category secondLevel = secondCategories.isEmpty() ? null : secondCategories.get(0);

        if (secondLevel == null) {
            secondLevel = new Category();
            secondLevel.setName(request.getSecondLevelCategory());
            secondLevel.setParentCategory(topLevel);
            secondLevel.setLevel(2);
            secondLevel = categoryRepository.save(secondLevel);
        }

        // Get or create third-level category
        List<Category> thirdCategories = categoryRepository.findByNameAndParent(request.getThirdLevelCategory(), secondLevel.getName());
        Category thirdLevel = thirdCategories.isEmpty() ? null : thirdCategories.get(0);

        if (thirdLevel == null) {
            thirdLevel = new Category();
            thirdLevel.setName(request.getThirdLevelCategory());
            thirdLevel.setParentCategory(secondLevel);
            thirdLevel.setLevel(3);
            thirdLevel = categoryRepository.save(thirdLevel);
        }

        // Create Product
        Product product = new Product();
        product.setTitle(request.getTitle());
        product.setColor(request.getColor());
        product.setDescription(request.getDescription());
        product.setDiscountPrice(request.getDiscountPrice());
        product.setDiscountPresent(request.getDiscountPresent());
        product.setImageUrl(request.getImageUrl());
        product.setBrand(request.getBrand());
        product.setPrice(request.getPrice());
        product.setSizes(request.getSize());
        product.setQuantity(request.getQuantity());
        product.setCategory(thirdLevel);
        product.setCreatedAt(LocalDateTime.now());

        return productRepository.save(product);
    }


    @Override
    public String deleteProduct(Long productId) throws ProductException {

        Product product = findProductById(productId);
        product.getSizes().clear();
        productRepository.delete(product);
        return "Product deleted Successfully";
    }

    @Override
    public Product updateProduct(Long productId, Product req) throws ProductException {
        Product product = findProductById(productId);
        if(req.getQuantity() != 0){
            product.setQuantity(req.getQuantity());
        }
        return productRepository.save(product);
    }

    @Override
    public Product findProductById(Long id) throws ProductException {
        Optional<Product> opt = productRepository.findById(id);

        if(opt.isPresent()){
            return opt.get();
        }
        throw new ProductException("Product not found with id -" + id);
    }

    @Override
    public List<Product> findProductByCategory(String category) {

        return null;
    }

    @Override
    public Page<Product> getAllProduct(
            String category,
            List<String> color,
            List<String> size,
            Integer minPrice,
            Integer maxPrice,
            Integer minDiscount,
            String sort,
            String stock,
            Integer pageNumber,
            Integer pageSize) {

        // Default values
        if (minPrice == null) minPrice = 0;
        if (maxPrice == null) maxPrice = 1_000_000;
        if (minDiscount == null) minDiscount = 0;
        if (sort == null || sort.isEmpty()) sort = "price_low";
        if (stock == null || stock.isEmpty()) stock = "all";
        if (pageNumber == null) pageNumber = 0;
        if (pageSize == null) pageSize = 1;

        Pageable pageable = PageRequest.of(pageNumber, pageSize);

        // Step 1: Fetch products from repository
        List<Product> products;
        if (category == null || category.isEmpty() || category.equalsIgnoreCase("all")) {
            products = productRepository.findAll();
        } else {
            products = productRepository.filterProducts(category, minPrice, maxPrice, minDiscount);
        }

        // Step 2: Filter by colors
        if (color != null && !color.isEmpty()) {
            products = products.stream()
                    .filter(p -> color.stream()
                            .anyMatch(c -> p.getColor() != null && c.equalsIgnoreCase(p.getColor())))
                    .collect(Collectors.toList());
        }

        // Step 3: Filter by sizes
        if (size != null && !size.isEmpty()) {
            products = products.stream()
                    .filter(p -> p.getSizes() != null && p.getSizes().stream()
                            .anyMatch(sizeObj -> size.stream()
                                    .anyMatch(s -> s.equalsIgnoreCase(sizeObj.getName()))))
                    .collect(Collectors.toList());
        }

        // Step 4: Filter by stock
        if ("in_stock".equalsIgnoreCase(stock)) {
            products = products.stream()
                    .filter(p -> p.getQuantity() > 0)
                    .collect(Collectors.toList());
        } else if ("out_of_stock".equalsIgnoreCase(stock)) {
            products = products.stream()
                    .filter(p -> p.getQuantity() < 1)
                    .collect(Collectors.toList());
        }

        // Step 5: Sort products
        if ("price_low".equalsIgnoreCase(sort)) {
            products.sort(Comparator.comparingInt(Product::getDiscountPrice));
        } else if ("price_high".equalsIgnoreCase(sort)) {
            products.sort(Comparator.comparingInt(Product::getDiscountPrice).reversed());
        } else if ("newest".equalsIgnoreCase(sort)) {
            products.sort(Comparator.comparing(Product::getCreatedAt).reversed());
        }

        // Step 6: Pagination
        int startIndex = (int) pageable.getOffset();
        int endIndex = Math.min(startIndex + pageable.getPageSize(), products.size());

        List<Product> pageContent = (startIndex < endIndex)
                ? products.subList(startIndex, endIndex)
                : List.of();

        return new PageImpl<>(pageContent, pageable, products.size());
    }


}
