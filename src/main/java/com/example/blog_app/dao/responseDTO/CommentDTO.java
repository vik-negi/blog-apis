package com.example.blog_app.dao.responseDTO;

import com.example.blog_app.bens.Comment;
import com.example.blog_app.bens.Post;
import com.example.blog_app.bens.User;
import com.example.blog_app.dao.CommentDAO;
import com.fasterxml.jackson.annotation.JsonIgnore;
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
public class CommentDTO {
    private Long id;
    public String content;

    @JsonIgnore
    private Post post;
    private User user;
    private LocalDateTime createdAt;
    private LocalDateTime editedAt;
    private boolean isEdited;
    @JsonIgnore
    private Comment parentComment;
    private List<Comment> replies;

    @JsonIgnore
    private List<User> likedBy;
    private int likeCount;
    private int replyCount;
    private boolean liked;
    private Long parentCommentId;

    public  CommentDTO(Comment comment, Long userId){
        this.id = comment.getId();
        this.content = comment.getContent();
        this.post = comment.getPost();
        this.user = comment.getUser();
        this.createdAt = comment.getCreatedAt();
        this.editedAt = comment.getEditedAt();
        this.isEdited = comment.getEditedAt()!= null;
        this.parentComment = comment.getParentComment();
        this.replies = comment.getReplies() == null ? null : comment.getReplies().subList(0, comment.getReplies().size() > 5 ? 5: comment.getReplies().size());
        this.likeCount = comment.getLikedBy() == null ? 0: comment.getLikedBy().size();
        this.replyCount = comment.getReplies() == null ? 0: comment.getReplies().size();
        this.liked = comment.getLikedBy() != null && comment.getLikedBy().stream().anyMatch(user -> user.getId().equals(userId));
        this.parentCommentId = comment.getParentCommentId();
    }

}
