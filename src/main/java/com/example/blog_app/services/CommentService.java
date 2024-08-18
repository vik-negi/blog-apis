package com.example.blog_app.services;

import com.example.blog_app.bens.Comment;
import com.example.blog_app.bens.Post;
import com.example.blog_app.bens.User;
import com.example.blog_app.dao.CommentDAO;
import com.example.blog_app.dao.PostDAO;
import com.example.blog_app.dao.UserDAO;
import com.example.blog_app.dao.responseDTO.CommentDTO;
import com.example.blog_app.dao.responseDTO.UserDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CommentService {

    @Autowired
    private CommentDAO commentDAO;

    @Autowired
    private PostDAO postDAO;

    @Autowired
    private UserDAO userDAO;

    public List<CommentDTO> getComments(Long postId, Long userId) {
        Post post = postDAO.findById(postId).orElseThrow(() -> new RuntimeException("Could not find Post"));
        List<Comment> comments = commentDAO.findAllByPostAndParentCommentIsNull(post);
        return comments.stream().map(comment -> new CommentDTO(comment, userId)).collect(Collectors.toList());
    }

    public CommentDTO addComment(Long postId, Long userId, String content, Long parentcomment){
        Post post = postDAO.findById(postId).orElseThrow(() -> new RuntimeException("Could not find Post"));
        User user = userDAO.findById(userId).orElseThrow(()-> new RuntimeException("Couldn't found user"));
        Comment comment = new Comment();
        comment.setContent(content);
        comment.setPost(post);
        comment.setUser(user);
        if(parentcomment!= null){
            Comment parentCommentEntity = commentDAO.findById(parentcomment).orElseThrow(() -> new RuntimeException("Could not find parent comment"));
            comment.setParentComment(parentCommentEntity);
            parentCommentEntity.getReplies().add(comment);

            comment = commentDAO.save(parentCommentEntity);
        }else{
            comment = commentDAO.save(comment);
        }

        return new CommentDTO(comment, userId);
    }

    public String deleteComment(Long commentId, Long userId){
        Comment comment = commentDAO.findById(commentId).orElseThrow(() -> new RuntimeException("Could not find Comment"));
        if(comment.getUser().getId().equals(userId)){
            commentDAO.delete(comment);
            return "Comment deleted successfully";
        }
        return "You don't have permission to delete this comment";
    }

}
