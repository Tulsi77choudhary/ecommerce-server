package com.example.E_Commerce.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Data
public class Review {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private  Long id;

    @Column
    private String review;

    @ManyToOne
    @JoinColumn(name = "product_id")
    @JsonIgnore
    private  Product product;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private  User user;

    private LocalDateTime createAt;
}
