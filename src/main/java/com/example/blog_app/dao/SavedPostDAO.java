package com.example.blog_app.dao;


import com.example.blog_app.bens.Post;
import com.example.blog_app.bens.SavedPost;
import com.example.blog_app.bens.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SavedPostDAO extends JpaRepository<SavedPost, Long> {
    boolean existsByUserAndPost(User user, Post post);
    void deleteByUserAndPost(User user, Post post);

    List<SavedPost> findAllByUser(User user);

}
