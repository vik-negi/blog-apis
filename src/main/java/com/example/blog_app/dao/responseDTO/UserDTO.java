package com.example.blog_app.dao.responseDTO;

import com.example.blog_app.bens.*;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO {
    private Long id;
    private String username;
    private String email;
    private String fullName;
    private Image image;
    private String bio;
    private LocalDateTime createdAt;
    private int followerCount;
    private int followingCount;
    private Follow follow;

    public UserDTO(User user, Long currentUserId) {
        this.id = user.getId();
        this.username = user.getUsername();
        this.fullName = user.getFullName();
        this.email = user.getEmail();
        this.bio = user.getBio();
        this.createdAt = user.getCreatedAt();
        if (user.getImage() != null) {
            this.image = (user.getImage());
        }
        this.followerCount = user.getFollowers().size();
        this.followingCount = user.getFollowing().size();
        this.follow = user.getFollowers().stream().filter(follower -> follower.getFollower().getId().equals(currentUserId)).findFirst().orElse(null);
    }
}
