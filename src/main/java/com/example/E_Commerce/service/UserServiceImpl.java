package com.example.E_Commerce.service;

import com.example.E_Commerce.Config.JwtUtil;
import com.example.E_Commerce.DTO.SignupRequest;
import com.example.E_Commerce.exception.UserException;
import com.example.E_Commerce.model.Cart;
import com.example.E_Commerce.model.User;
import com.example.E_Commerce.repo.CartRepository;
import com.example.E_Commerce.repo.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService{
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CartRepository cartRepository;
    @Autowired
    private JwtUtil jwtUtil;


    @Autowired
    private PasswordEncoder passwordEncoder;
    @Override
    public User signup(SignupRequest signupRequest) throws UserException {
        if (userRepository.findByEmail(signupRequest.getEmail()).isPresent()) {
            throw new UserException("Email already registered!");
        }

        User user = new User();
        user.setFirstName(signupRequest.getFirstName());
        user.setLastName(signupRequest.getLastName());
        user.setEmail(signupRequest.getEmail());
        user.setPassword(passwordEncoder.encode(signupRequest.getPassword()));
        User savedUser = userRepository.save(user);

         Cart cart = new Cart();
         cart.setUser(savedUser);
         cart.setTotalPrice(0);
         cart.setCartItems(null);

         cartRepository.save(cart);

        return savedUser;
    }

    @Override
    public User login(String email, String password) throws UserException {
       User user = userRepository.findByEmail(email)
               .orElseThrow(()-> new UserException("Invalid email or password"));

       if (!passwordEncoder.matches(password,user.getPassword())){
           throw new UserException("Invalid email or password");
       }

       return user;
    }

    @Override
    public User findUserById(Long id) throws UserException {
        return userRepository.findById(id)
                .orElseThrow(() -> new UserException("User not found with id - " + id));
    }


    @Override
    public User findUserProfileByJwt(String token) throws UserException {
        String email = jwtUtil.extractUsername(token);
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new UserException("User not found for this token"));
    }

}
