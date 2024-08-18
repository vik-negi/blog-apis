package com.example.blog_app.dao;

import com.example.blog_app.bens.Image;
import com.example.blog_app.bens.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ImageDAO extends JpaRepository<Image, Long> {

}
