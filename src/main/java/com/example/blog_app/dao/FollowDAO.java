package com.example.blog_app.dao;

import com.example.blog_app.bens.Follow;
import com.example.blog_app.bens.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FollowDAO extends JpaRepository<Follow, Long> {

    void deleteByFollowerAndFollowing(User follower, User following);
}
