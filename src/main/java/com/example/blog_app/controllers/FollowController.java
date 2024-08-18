package com.example.blog_app.controllers;

import com.example.blog_app.services.FollowService;
import com.example.blog_app.utils.security.JWTHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/follow")
public class FollowController {

    @Autowired
    private FollowService service;

    @Autowired
    private JWTHelper jwtHelper;

    @GetMapping("/users")
    public ResponseEntity<?> getUserFollowers(@RequestParam Long id) {
        try {
            return ResponseEntity.ok(service.getUserFollowers(id));
        } catch (Exception e) {
            return ResponseEntity.status(500).body(e.getMessage());
        }
    }

    @GetMapping("/following/users")
    public ResponseEntity<?> getUserFollowing(@RequestParam Long id) {
        try {
            return ResponseEntity.ok(service.getUserFollowing(id));
        } catch (Exception e) {
            return ResponseEntity.status(500).body(e.getMessage());
        }
    }
    @PostMapping("/")
    public ResponseEntity<?> followUser(@RequestHeader("authorization") String headerAuthorization, @RequestParam Long userId) {
        try {
            Long id = jwtHelper.getUserIdFromToken(headerAuthorization);

            return ResponseEntity.ok(service.follow(id, userId));
        }catch (Exception e) {
            return ResponseEntity.status(500).body(e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> unfollowUser(@RequestHeader("authorization") String headerAuthorization, @PathVariable Long id) {
        try {
            Long userId = jwtHelper.getUserIdFromToken(headerAuthorization);

            return ResponseEntity.ok(service.unfollow(userId, id));
        } catch (Exception e) {
            return ResponseEntity.status(500).body(e.getMessage());
        }
    }

    @PatchMapping("/{id}")
    public ResponseEntity<?> acceptFollowRequest(@PathVariable Long id) {
        try {
            service.acceptFollow(id);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.status(500).body(e.getMessage());
        }
    }
}