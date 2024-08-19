package com.example.blog_app.controllers;

import com.example.blog_app.bens.Image;
import com.example.blog_app.bens.User;
import com.example.blog_app.dao.requestDAO.RegisterUserDAO;
import com.example.blog_app.services.AuthService;
import com.example.blog_app.services.CloudinaryService;
import com.example.blog_app.services.PasswordService;
import com.example.blog_app.utils.security.JWTHelper;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    public AuthService authService;

    @Autowired
    public CloudinaryService cloudinaryService;

    @Autowired
    private JWTHelper jwtHelper;

    private final Logger logger = LoggerFactory.getLogger(OncePerRequestFilter.class);


    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Map<String, String> body){
        try{
            User user = authService.login(body.get("email"), body.get("password"));
            Map<String, Object> data = new HashMap<>();
            String token = this.jwtHelper.generateToken(user.getId());

            data.put("token", token);
            data.put("user", user);
            return ResponseEntity.ok(data);
        }catch (Exception e){
            return ResponseEntity.status(401).body(e.getMessage());
        }
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@ModelAttribute @Valid RegisterUserDAO userData, BindingResult bindingResult){
        try{
            MultipartFile imageFile = userData.getImage();
            User user = new User();
            user.setUsername(userData.getUsername());
            user.setFullName(userData.getFullName());
            user.setEmail(userData.getEmail());
            user.setBio(userData.getBio());
            logger.info("Register : " + user.getFullName() + " " + user.getEmail() + " " + user.getEmail() + " " + user.getPassword());
            User savedUSer = authService.register(user);
            PasswordService passwordService = new PasswordService();
            String hasPassword = passwordService.hashPassword(userData.getPassword());
            user.setPassword(hasPassword);


            if(imageFile != null){
                Image image = cloudinaryService.uploadImage(imageFile);
//                image.setUser(savedUSer);
                savedUSer.setImage(image);
            }
            authService.register(savedUSer);
            Map<String, Object> data = new HashMap<>();
            data.put("token", "Thisisdummytoken");
            data.put("user", user);
            return ResponseEntity.ok(data);
        }catch (Exception e){
            return ResponseEntity.status(500).body(e.getMessage());
        }
    }

}
