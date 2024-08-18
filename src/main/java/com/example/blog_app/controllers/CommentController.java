package com.example.blog_app.controllers;

import com.example.blog_app.dao.requestDAO.CommentRequestDTO;
import com.example.blog_app.dao.responseDTO.CommentDTO;
import com.example.blog_app.services.CommentService;
import com.example.blog_app.utils.security.JWTHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/comments")
public class CommentController {

    @Autowired
    private JWTHelper jwtHelper;
    @Autowired
    private CommentService service;

    @GetMapping("/{postId}")
    public ResponseEntity<?> getComments(@PathVariable Long postId, @RequestHeader("authorization") String headerAuthorization) {
        try {
            Long userId = jwtHelper.getUserIdFromToken(headerAuthorization);
            return ResponseEntity.ok(service.getComments(postId, userId));
        } catch (Exception e) {
            return ResponseEntity.status(500).body(e.getMessage());
        }
    }

    @PostMapping("")
    public ResponseEntity<?> addComment(@RequestBody CommentRequestDTO body, @RequestHeader("authorization") String headerAuthorization){
        try{
            Long userId = jwtHelper.getUserIdFromToken(headerAuthorization);
            CommentDTO commentDTO = service.addComment(body.getPostId(), userId, body.getContent(), body.getParentcommentId());
            return ResponseEntity.ok(commentDTO);
        }
        catch (Exception e){
            return ResponseEntity.status(500).body(e.getMessage());
        }
    }

    @DeleteMapping("/{commentId}")
    public ResponseEntity<?> deleteComment(@PathVariable Long commentId, @RequestHeader("authorization") String headerAuthorization){
        try{
            Long userId = jwtHelper.getUserIdFromToken(headerAuthorization);
            return ResponseEntity.ok(service.deleteComment(commentId,userId ));
        }catch (Exception e){
            return ResponseEntity.status(500).body(e.getMessage());
        }
    }
}
