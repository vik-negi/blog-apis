package com.example.blog_app.services;

import com.example.blog_app.bens.Follow;
import com.example.blog_app.bens.User;
import com.example.blog_app.dao.FollowDAO;
import com.example.blog_app.dao.UserDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FollowService {

    @Autowired
    UserDAO userDAO;

    @Autowired
    FollowDAO followDAO;


    public List<Follow> getUserFollowers(Long userId){
        User user = userDAO.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));
        List<Follow> followers = user.getFollowers();
        return followers;
    }

    public List<Follow> getUserFollowing(Long userId){
        User user = userDAO.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));
        List<Follow> following = user.getFollowing();
        return following;
    }
    public Follow follow(Long userId, Long otherUserId) {
        User user = userDAO.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));
        User otherUser = userDAO.findById(otherUserId).orElseThrow(() -> new RuntimeException("User not found"));
        Follow follow = new Follow();
        follow.setFollower(user);
        follow.setFollowing(otherUser);
        return followDAO.save(follow);
    }

    public String unfollow(Long userId, Long followId) {
        Follow follow = followDAO.findById(followId).orElseThrow(() -> new RuntimeException("User hasn't followed by you"));
        if(follow != null && follow.getFollower().getId().equals(userId)) {
            followDAO.deleteById(followId);
            return "un-follow successful";
        }
        throw new RuntimeException("You can't unfollow someone you haven't followed");
    }

    public void acceptFollow(Long followId) {
        Follow follow = followDAO.findById(followId).orElseThrow(() -> new RuntimeException("Follow request not found"));
        follow.setAccepted(true);
        followDAO.save(follow);
    }


}
