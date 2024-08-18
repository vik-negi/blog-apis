package com.example.blog_app.services;


import com.example.blog_app.bens.User;
import com.example.blog_app.dao.UserDAO;
import com.example.blog_app.dao.responseDTO.UserDTO;
import com.example.blog_app.utils.security.JWTHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserService {

    @Autowired
    public UserDAO userDAO;

    @Autowired
    JWTHelper jwthelper;


    public UserDTO findUserById(Long id, Long currentUser){
        User user =  userDAO.findById(id).orElseThrow(()-> new RuntimeException("User not found"));
        return new UserDTO(user, currentUser);
    }

    public User findUserByIdWithDTO(Long id){
        return  userDAO.findById(id).orElseThrow(()-> new RuntimeException("User not found"));

    }

    public List<UserDTO> getUsers(Long id){
            return userDAO.findAllExcept(id).stream()
                    .map(user -> new UserDTO(user, id)).collect(Collectors.toList());
    }

    public User updateUser(User user){
        return userDAO.save(user);
    }


}
