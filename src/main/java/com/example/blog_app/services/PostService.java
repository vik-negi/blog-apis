package com.example.blog_app.services;

import com.example.blog_app.bens.Post;
import com.example.blog_app.bens.User;
import com.example.blog_app.dao.PostDAO;
import com.example.blog_app.dao.UserDAO;
import com.example.blog_app.dao.responseDTO.PostResponseDTO;
import org.hibernate.Hibernate;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;

import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PostService {
    @Autowired
    private PostDAO postDAO;

    @Autowired
    private UserDAO userDAO;


    public Post createPost(Post post) {
        postDAO.save(post);
        return post;
    }

    Sort sort = Sort.by(Sort.Order.desc("createdAt"));


    public Post getPostById(Long id){
        return postDAO.findById(id).orElseThrow(()-> new RuntimeException("Post not found"));
    }

    public PostResponseDTO getPostByIdWithDTO(Long id, Long userId){
        Post post = getPostById(id);
        return new PostResponseDTO(post, userId);
    }

    public List<PostResponseDTO> getPosts(Long userId){
        List<Post> posts = postDAO.findAllWithCreatedBy(sort);
        return posts.stream().map(post ->  new PostResponseDTO(
                    post, userId)).collect(Collectors.toList());
    }

    public List<PostResponseDTO> getUserPosts(Long id){

        List<Post> posts =  postDAO.findAllByCreatedBy_Id(id,sort);
        return posts.stream().map(post ->  new PostResponseDTO(
                post, id)).collect(Collectors.toList());
    }

    public List<PostResponseDTO> getUserLikedPosts(Long id){
        List<Post> posts = postDAO.findAllPostsLikedByUser(id,sort);
        return posts.stream().map(post ->  new PostResponseDTO(
                    post, id)).collect(Collectors.toList());
    }

    public String likePost(Long id, Long userId){
        Optional<Post> postOptional = postDAO.findById(id);
        Optional<User> userOptional = userDAO.findById(userId);
        if(postOptional.isPresent() && userOptional.isPresent()) {
            Post post = postOptional.get();
            User user = userOptional.get();
            if (!post.getLikedBy().contains(user)) {
                post.getLikedBy().add(user);
                postDAO.save(post);
                return "Post liked successfully";
            }
             new RuntimeException("Post already liked by user");
        }
        new RuntimeException("Post or User Not Found");
        return "Error liking post";
    }

    public String dislikePost(Long id, Long userId){
        Optional<Post> postOptional = postDAO.findById(id);
        Optional<User> userOptional = userDAO.findById(userId);
        if(postOptional.isPresent() && userOptional.isPresent()) {
            Post post = postOptional.get();
            User user = userOptional.get();
            if (post.getLikedBy().contains(user)) {
                post.getLikedBy().remove(user);
                postDAO.save(post);
                return "Post disliked successfully";
            }
             new RuntimeException("Post not liked by user");
        }
        new RuntimeException("Post or User Not Found");
        return "Error disliking post";
    }

    public void deletePost(Long id){
        postDAO.deleteById(id);
    }

//    public List<User> getPostLikedByUser(Long id){
//        Post post = postDAO.findById(id).orElseThrow(() -> new RuntimeException("Not found"));
//
//
//    }

    public Post updatePost(Post post, Long id){
        Post existing = postDAO.findById(id).orElseThrow(()-> new RuntimeException("Not found"));
        try{
//            BeanUtils.copyProperties(existing, post);
//            List<String> img = post.getImages();
//            existing.setId(id);
//            if(img != null){
//                existing.setImages(img);
//                post.setImages(img);
//            }

            existing.setDescription(post.getDescription());
            existing.setImages(post.getImages());
            return postDAO.save(existing);
        }catch(Exception e){
            throw new RuntimeException("Error updating post");
        }

    }
}
