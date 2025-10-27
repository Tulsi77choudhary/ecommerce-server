package com.example.E_Commerce.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Data
public class Rating {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "user_id",nullable = false)
    private User user;

    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "product_id",nullable = false)
    private Product product;

    @Column(name = "rating")
    private double rating;

    private LocalDateTime createAt;

}
