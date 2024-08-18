package com.example.blog_app.dao;

import com.example.blog_app.bens.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AuthDAO extends JpaRepository<User, Long> {

    User findByUsername(String username);

    User findByEmail(String email);
}
