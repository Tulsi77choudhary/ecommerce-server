package com.example.E_Commerce.service;

import com.example.E_Commerce.DTO.SignupRequest;
import com.example.E_Commerce.exception.UserException;
import com.example.E_Commerce.model.User;

public interface UserService {

     User signup(SignupRequest signupRequest) throws UserException;

     User login(String email, String password) throws UserException;

     User findUserById(Long id) throws UserException;
     User findUserProfileByJwt(String token) throws UserException;


}
