package com.example.E_Commerce.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;
import lombok.ToString;

import java.time.LocalDateTime;

@Entity
@Data
public class OrderItem {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @JsonIgnore
    @ManyToOne
    @ToString.Exclude
    private  Order order;

    @ManyToOne
    private Product product;
    private String size;

    private int quantity;

    private int price;

    private int discountedPrice;

    private Long userId;

    private LocalDateTime deliveryDate;
}
