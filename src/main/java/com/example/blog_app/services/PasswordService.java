package com.example.blog_app.services;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

public class PasswordService {
    private final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public String hashPassword(String password){
        return passwordEncoder.encode(password);
    }

    public boolean matches(String plainPassword, String hashedPassword){
        return passwordEncoder.matches(plainPassword, hashedPassword);
    }

}
