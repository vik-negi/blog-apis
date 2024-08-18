package com.example.blog_app.services;

import com.example.blog_app.bens.Post;
import com.example.blog_app.bens.SavedPost;
import com.example.blog_app.bens.User;
import com.example.blog_app.dao.PostDAO;
import com.example.blog_app.dao.SavedPostDAO;
import com.example.blog_app.dao.UserDAO;
import com.example.blog_app.dao.responseDTO.PostResponseDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class SavedPostService {
    @Autowired
    private  SavedPostDAO savedPostDAO;
    @Autowired

    private  PostDAO postDAO;
    @Autowired

    private  UserDAO userDAO;

    public  void savePost(Long userId, Long postId){
        User user = userDAO.findById(userId).orElseThrow(()-> new RuntimeException("User not found"));
        Post post = postDAO.findById(postId).orElseThrow(()-> new RuntimeException("Post not found"));
        if(!savedPostDAO.existsByUserAndPost(user, post)){
            SavedPost savedPost = new SavedPost();
            savedPost.setPost(post);
            savedPost.setUser(user);
            savedPostDAO.save(savedPost);
        }
    }

    @Transactional
    public void unSavePost(Long userId, Long postId){
        User user = userDAO.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));
        Post post = postDAO.findById(postId).orElseThrow(() -> new RuntimeException("Post not found"));

        savedPostDAO.deleteByUserAndPost(user, post);
    }

    public List<PostResponseDTO> getSavedPosts(Long userId){
        User user = userDAO.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));
        return savedPostDAO.findAllByUser(user).stream().map(SavedPost ::getPost).map(post ->  new PostResponseDTO(
                post, userId)).collect(Collectors.toList());

    }

}
