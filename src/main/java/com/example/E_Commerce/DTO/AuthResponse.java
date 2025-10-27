package com.example.E_Commerce.DTO;

import com.example.E_Commerce.model.User;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AuthResponse {
    //private User user;
    private String token;
    private String message;
    private User user;
}
