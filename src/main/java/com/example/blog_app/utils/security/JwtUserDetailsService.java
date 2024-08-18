package com.example.blog_app.utils.security;

import com.example.blog_app.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.ArrayList;

@Component
public class JwtUserDetailsService implements UserDetailsService {
    @Autowired
    private UserService userService;


    @Override
    public UserDetails loadUserByUsername(String userId) throws UsernameNotFoundException {
        com.example.blog_app.bens.User user = userService.findUserByIdWithDTO(Long.valueOf(userId));
        if (user == null) {
            throw new UsernameNotFoundException("User not found with userId: " + userId);
        }
        return new User(user.getUsername(), user.getPassword(), new ArrayList<>());
    }
}
