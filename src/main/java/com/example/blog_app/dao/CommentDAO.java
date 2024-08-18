package com.example.blog_app.dao;

import com.example.blog_app.bens.Comment;
import com.example.blog_app.bens.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentDAO extends JpaRepository<Comment, Long> {

    List<Comment> findAllByPostAndParentCommentIsNull(Post post);

}
