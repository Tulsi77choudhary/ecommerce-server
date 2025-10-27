package com.example.E_Commerce.controller;

import com.example.E_Commerce.Config.JwtUtil;
import com.example.E_Commerce.DTO.AuthResponse;
import com.example.E_Commerce.DTO.SignupRequest;
import com.example.E_Commerce.DTO.LoginRequest;
import com.example.E_Commerce.exception.UserException;
import com.example.E_Commerce.model.User;
import com.example.E_Commerce.service.CartService;
import com.example.E_Commerce.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@CrossOrigin(origins = "http://localhost:5173")
public class AuthController {

    @Autowired
    private CartService cartService;
    @Autowired
    private UserService userService;

    @Autowired
    private JwtUtil jwtUtil;

    @PostMapping("/signup")
    public ResponseEntity<AuthResponse> signup(@RequestBody SignupRequest signupRequest) throws UserException {
        User savedUser = userService.signup(signupRequest);
        String token = jwtUtil.generateToken(savedUser.getEmail());
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new AuthResponse(token, "Signup Success",savedUser));
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody LoginRequest loginRequest) throws UserException {
        User user = userService.login(loginRequest.getEmail(), loginRequest.getPassword());
        String token = jwtUtil.generateToken(user.getEmail());
        return ResponseEntity.ok(new AuthResponse(token, "Login Success",user));
    }
}

