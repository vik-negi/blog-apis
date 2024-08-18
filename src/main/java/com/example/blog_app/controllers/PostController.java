package com.example.blog_app.controllers;

import com.example.blog_app.bens.Image;
import com.example.blog_app.bens.Post;
import com.example.blog_app.bens.User;
import com.example.blog_app.dao.requestDAO.PostRequestDAO;
import com.example.blog_app.services.CloudinaryService;
import com.example.blog_app.services.ImageService;
import com.example.blog_app.services.PostService;
import com.example.blog_app.services.UserService;
import com.example.blog_app.utils.security.JWTHelper;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

@RequestMapping("/api/post")
@RestController
public class PostController {


    @Autowired
    PostService postService;

    private final Logger logger = LoggerFactory.getLogger(OncePerRequestFilter.class);
    @Autowired
    UserService userService;

    @Autowired
    JWTHelper jwtHelper;

    @Autowired
    ImageService imageService;

//    @Autowired
//    private ImageDAO imageDAO;

    @Autowired
    CloudinaryService cloudinaryService;
    @PostMapping("/")
    public ResponseEntity<?> createPost(@ModelAttribute @Valid PostRequestDAO postRequest, BindingResult bindingResult){
        if (bindingResult.hasErrors()) {
            return new ResponseEntity<>(bindingResult.getAllErrors(), HttpStatus.BAD_REQUEST);
        }
        try {

            Long createdById = postRequest.getCreatedBy();
            User createdBy = userService.findUserByIdWithDTO(createdById);
            Post post = new Post();
            post.setCreatedBy(createdBy);
            post.setDescription(postRequest.getDescription());
            Post savedPost = postService.createPost(post);
            MultipartFile[] files = postRequest.getFiles();
            if(files != null && files.length > 0){
                List<Image> images = new ArrayList<Image>();
                for (MultipartFile file : files) {
                    Image image = cloudinaryService.uploadImage(file);
//                    Image savedImage = imageDAO.save(image);
                    image.setPost(savedPost);
                    images.add(image);
                }
                savedPost.setImages(images);
            }
            Post response = postService.createPost(post);
            return new ResponseEntity<>(response, HttpStatus.CREATED);
        }catch (Exception e) {

            return new ResponseEntity<>(
                    e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR
            );
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getPostById(@PathVariable Long id, @RequestHeader("authorization") String headerAuthorization){
        try{
            Long userId = jwtHelper.getUserIdFromToken(headerAuthorization);
            return new ResponseEntity<>(postService.getPostByIdWithDTO(id, userId), HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(
                    e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR
            );
        }
    }

    @GetMapping("")
    public ResponseEntity<?> getPosts(@RequestHeader("authorization") String headerAuthorization){
        try{
            Long userId = jwtHelper.getUserIdFromToken(headerAuthorization);
            return new ResponseEntity<>(postService.getPosts(userId), HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/user/{id}")
    public ResponseEntity<?> getUserPosts(@PathVariable Long id){
        try{
            return new ResponseEntity<>(postService.getUserPosts(id), HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/user/liked-posts/{id}")
    public ResponseEntity<?> getUserLikedPosts(@PathVariable Long id){
        try{
            return new ResponseEntity<>(postService.getUserLikedPosts(id), HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PatchMapping("/like/{id}")
    public ResponseEntity<?> likePost(@PathVariable("id") Long id, @RequestHeader("authorization") String authorizationHeader){
        try{
            String token =  authorizationHeader;
            Long userId = jwtHelper.getUserIdFromToken(token);
            postService.likePost(id, userId);

            return new ResponseEntity<>("Post liked successfully", HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PatchMapping("/dislike/{id}")
    public ResponseEntity<?> dislikePost(@PathVariable("id") Long id, @RequestHeader("authorization") String authorizationHeader){
        try{
            String token = authorizationHeader.startsWith("Bearer ") ? authorizationHeader.substring(7) : authorizationHeader;
            Long userId = jwtHelper.getUserIdFromToken(token);
            postService.dislikePost(id, userId);
            return new ResponseEntity<>("Post disliked successfully", HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deletePost(@PathVariable Long id){
        try{
            Post post = postService.getPostById(id);
            if(post == null) return new ResponseEntity<>("Post not found", HttpStatus.NOT_FOUND);
            if(post.getImages() != null){
                for(Image image : post.getImages()) {
                    cloudinaryService.deleteImage(image.getImageId());
                    imageService.deleteImage(image.getId());
                }
            }
            postService.deletePost(id);
            return new ResponseEntity<>("Post deleted successfully", HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updatePost(@PathVariable Long id, @RequestBody Post post){
        try{
            postService.updatePost(post, id);
            return new ResponseEntity<>(post, HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
