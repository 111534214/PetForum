package com.example.demo.service;

import com.example.demo.entity.Comment;
import com.example.demo.repository.CommentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Optional;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class CommentService {
    @Autowired
    private CommentRepository commentRepository;

    public List<Comment> findByUserId(Long userId) {
        return commentRepository.findByUserId(userId);
    }

    public void deleteById(Long commentId) {
        commentRepository.deleteById(commentId);
    }

    public Comment updateComment(Long id, String content) {
        Optional<Comment> optional = commentRepository.findById(id);
        if (optional.isPresent()) {
            Comment comment = optional.get();
            comment.setContent(content);
            comment.setUpdatedAt(LocalDateTime.now());
            return commentRepository.save(comment);
        }
        return null;
    }
}
//
//package com.example.demo.service;
//
//import com.example.demo.entity.Comment;
//import com.example.demo.repository.CommentRepository;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
//import java.util.List;
//import java.util.Optional;
//import java.time.LocalDateTime;
//
//@Service
//public class CommentService {
//    @Autowired
//    private CommentRepository commentRepository;
//
//    public List<Comment> findByUserId(Long userId) {
//        return commentRepository.findByUserId(userId);
//    }
//
//    public void deleteById(Long commentId) {
//        commentRepository.deleteById(commentId);
//    }
//
//    public Comment updateComment(Long id, String content) {
//        Optional<Comment> optional = commentRepository.findById(id);
//        if (optional.isPresent()) {
//            Comment comment = optional.get();
//            comment.setContent(content);
//            comment.setUpdatedAt(LocalDateTime.now());
//            return commentRepository.save(comment);
//        }
//        return null;
//    }
//
//
//}