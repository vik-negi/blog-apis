package com.example.blog_app.controllers;

import com.example.blog_app.bens.Image;
import com.example.blog_app.bens.User;
import com.example.blog_app.dao.requestDAO.PostRequestDAO;
import com.example.blog_app.dao.requestDAO.UserRequestDAO;
import com.example.blog_app.dao.responseDTO.UserDTO;
import com.example.blog_app.services.CloudinaryService;
import com.example.blog_app.services.UserService;
import com.example.blog_app.utils.security.JWTHelper;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.filter.OncePerRequestFilter;

import java.util.Optional;

import static org.springframework.data.jpa.domain.AbstractPersistable_.id;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    public UserService userService;

    @Autowired
    private JWTHelper jwtHelper;

    @Autowired
    public CloudinaryService cloudinaryService;

    @GetMapping("/{id}")
    public ResponseEntity<?> getUser(@PathVariable Long id ,@RequestHeader("authorization") String headerAuthorization){
        try{
            Long userId = jwtHelper.getUserIdFromToken(headerAuthorization);
            UserDTO user = userService.findUserById(id,userId);
            return ResponseEntity.ok(user);
        }catch (Exception e){
            return ResponseEntity.status(500).body(e.getMessage());
        }
    }

    @GetMapping("")
    public ResponseEntity<?> getUsers(@RequestHeader("authorization") String headerAuthoriza){
        try{
            Long userId = jwtHelper.getUserIdFromToken(headerAuthoriza);
            return ResponseEntity.ok(userService.getUsers(userId));
        }catch (Exception e){
            return ResponseEntity.status(500).body(e.getMessage());
        }
    }

    @PatchMapping("")
    public ResponseEntity<?> updateUser(@ModelAttribute @Valid UserRequestDAO userRequest, BindingResult bindingResult){
        try{
            if(bindingResult.hasErrors()){
                return ResponseEntity.badRequest().body(bindingResult.getAllErrors());
            }
            Long id = userRequest.getId();
            User user = userService.findUserByIdWithDTO(id);
            if (user == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
            }

            user.setBio(userRequest.getBio());
            user.setFullName(userRequest.getFullName());
            user.setUsername(userRequest.getUsername());
            if (userRequest.getImage() != null) {
                Image image = cloudinaryService.uploadImage(userRequest.getImage());
//                Image savedImage = imageDAO.save(image);
                user.setImage(image);

//                user.setImage(savedImage);
            }
            return ResponseEntity.ok(userService.updateUser(user));
        }catch (Exception e){
            return ResponseEntity.status(500).body(e.getMessage());
        }
    }
}
