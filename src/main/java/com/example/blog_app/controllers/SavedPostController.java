package com.example.blog_app.controllers;

import com.example.blog_app.bens.Post;
import com.example.blog_app.dao.responseDTO.PostResponseDTO;
import com.example.blog_app.services.SavedPostService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.filter.OncePerRequestFilter;

import java.util.List;

@RestController
@RequestMapping("/api/saved-posts")
public class SavedPostController {
    @Autowired
    private SavedPostService savedPostService;

    private final Logger logger = LoggerFactory.getLogger(OncePerRequestFilter.class);

    @PostMapping("/save")
    public ResponseEntity<?> savePost(@RequestParam Long userId, @RequestParam Long postId) {
        logger.info("-----------------Saving post "+ userId + " and post "+ postId);
        try {
            savedPostService.savePost(userId, postId);
            return ResponseEntity.ok("Post saved successfully");
        }catch (Exception e) {
            return ResponseEntity.status(500).body(e.getMessage());
        }
    }

    @PostMapping("/unsave")
    public ResponseEntity<?> unsavePost(@RequestParam Long userId, @RequestParam Long postId) {
        try{
            savedPostService.unSavePost(userId, postId);
            return ResponseEntity.ok("Post unsaved successfully");
        }catch (Exception e) {
            return ResponseEntity.status(500).body(e.getMessage());
        }
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<PostResponseDTO> getSavedPosts(@PathVariable Long userId) {
        return new ResponseEntity(savedPostService.getSavedPosts(userId), HttpStatus.OK);
    }

}
