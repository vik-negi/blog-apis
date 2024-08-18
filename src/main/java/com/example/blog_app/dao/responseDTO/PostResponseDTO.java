package com.example.blog_app.dao.responseDTO;

import com.example.blog_app.bens.Image;
import com.example.blog_app.bens.Post;
import com.example.blog_app.bens.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PostResponseDTO {
    private boolean isLiked; // Add this field
    private int likeCount;
    private int commentCount;
    private boolean isSaved;
    Long id;
    String description;
    private List<Image> images;
    private LocalDateTime createdAt;
    User createdBy;
    List<User> likedBy;
    public PostResponseDTO(Post post, Long userId) {
        this.id = (post.getId());
        this.description =(post.getDescription());
        this.images = post.getImages();
//                .stream()
//                .map(image -> new ImageDTO(image))
//                .collect(Collectors.toList());

        this.createdAt =(post.getCreatedAt());
        this.createdBy = (post.getCreatedBy());
        this.isLiked = post.getLikedBy()
                .stream()
                .anyMatch(user -> user.getId().equals(userId));
        this.likeCount = post.getLikedBy().size();
        this.isSaved = post.getSavedPosts()
                .stream()
                .anyMatch(user -> user.getUser().getId().equals(userId));
        this.commentCount = post.getComments() != null ?
                post.getComments().size() : 0;

    }
}



