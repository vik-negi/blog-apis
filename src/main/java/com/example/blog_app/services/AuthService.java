package com.example.blog_app.services;

import com.example.blog_app.bens.User;
import com.example.blog_app.dao.AuthDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Objects;

@Service
public class AuthService {

    @Autowired
    public AuthDAO authDAO;

    public User login(String email, String password) {
        User user = authDAO.findByEmail(email);
        if (user != null) {
            PasswordService passwordService = new PasswordService();
            boolean isMatched = passwordService.matches(password, user.getPassword());
            if (isMatched) {
                return user;
            }else{
                throw new RuntimeException("Invalid credentials");
            }
        }else{
            throw new RuntimeException("User does not exist");
        }

    }

    public User register(User user) {

        user.setCreatedAt(LocalDateTime.now());
        return authDAO.save(user);
    }
}
