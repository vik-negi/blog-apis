package com.example.blog_app.dao;

import com.example.blog_app.bens.Post;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PostDAO  extends JpaRepository<Post, Long> {


    @EntityGraph(attributePaths = {"createdBy"})
    List<Post> findAllWithCreatedBy(Sort sort);

    @Query("SELECT p from Post p JOIN p.likedBy u WHERE u.id = :userId")
    List<Post> findAllPostsLikedByUser(@Param("userId")  Long userId, Sort sort);

    @EntityGraph(attributePaths = {"createdBy"})
    List<Post> findAllByCreatedBy_Id(Long userId, Sort sort);

    @EntityGraph(attributePaths = {"createdBy"})
    Optional<Post> findById(Long id);
}
