package com.example.blog_app.dao;

import com.example.blog_app.bens.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface UserDAO extends JpaRepository<User, Long> {

    @Query("SELECT u FROM User u WHERE :id IS NULL OR u.id <> :id")
    List<User> findAllExcept(@Param("id") Long id);
}
