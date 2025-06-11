//package com.example.demo.controller;
//
//import com.example.demo.dto.CommentDto;
//import com.example.demo.entity.Comment;
//import com.example.demo.entity.User;
//import com.example.demo.entity.Post;
//import com.example.demo.repository.CommentRepository;
//import com.example.demo.repository.UserRepository;
//import com.example.demo.repository.PostRepository;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.*;
//import java.util.Map;
//import java.time.LocalDateTime;
//import java.util.Optional;
//import java.util.List;
//
//@RestController
//@RequestMapping("/api/comments")
//public class CommentController {
//
//    @Autowired
//    private CommentRepository commentRepository;
//
//    @Autowired
//    private UserRepository userRepository;
//
//    @Autowired
//    private PostRepository postRepository;
//
//    @Autowired
//    private CommentService commentService;
//
//    @PostMapping
//    public ResponseEntity<?> createComment(@RequestBody CommentDto commentDto) {
//        Optional<User> userOpt = userRepository.findById(commentDto.getUserId());
//        Optional<Post> postOpt = postRepository.findById(commentDto.getPostId());
//
//        if (userOpt.isEmpty() || postOpt.isEmpty()) {
//            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
//                    .body("User 或 Post 不存在");
//        }
//
//        Comment comment = new Comment();
//        comment.setUser(userOpt.get());
//        comment.setPost(postOpt.get());
//        comment.setContent(commentDto.getContent());
//        comment.setCreatedAt(LocalDateTime.now());
//
//        commentRepository.save(comment);
//
//        return ResponseEntity.ok(Map.of("message", "留言新增成功"));
//    }
//
//    // 新增這個 GET API
//    @GetMapping("/post/{postId}")
//    public List<CommentDto> getCommentsByPostId(@PathVariable Long postId) {
//        return commentRepository.findAllByPostIdWithUserInfo(postId);
//    }
//
//    @PutMapping("/{id}")
//    public ResponseEntity<?> updateComment(@PathVariable Long id, @RequestBody Map<String, String> req) {
//        String content = req.get("content");
//        if (content == null || content.trim().isEmpty()) {
//            return ResponseEntity.badRequest().body("內容不能為空");
//        }
//        Comment updated = commentService.updateComment(id, content);
//        if (updated != null) {
//            return ResponseEntity.ok(updated);
//        } else {
//            return ResponseEntity.notFound().build();
//        }
//    }
//}



package com.example.demo.controller;

import com.example.demo.dto.CommentDto;
import com.example.demo.entity.Comment;
import com.example.demo.entity.User;
import com.example.demo.entity.Post;
import com.example.demo.repository.CommentRepository;
import com.example.demo.repository.UserRepository;
import com.example.demo.repository.PostRepository;
import com.example.demo.service.CommentService; // <--- 記得import
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.Map;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.List;

@RestController
@RequestMapping("/api/comments")
public class CommentController {

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private CommentService commentService; // <-- 加這行

    @PostMapping
    public ResponseEntity<?> createComment(@RequestBody CommentDto commentDto) {
        Optional<User> userOpt = userRepository.findById(commentDto.getUserId());
        Optional<Post> postOpt = postRepository.findById(commentDto.getPostId());

        if (userOpt.isEmpty() || postOpt.isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("User 或 Post 不存在");
        }

        Comment comment = new Comment();
        comment.setUser(userOpt.get());
        comment.setPost(postOpt.get());
        comment.setContent(commentDto.getContent());
        comment.setCreatedAt(LocalDateTime.now());

        commentRepository.save(comment);

        return ResponseEntity.ok(Map.of("message", "留言新增成功"));
    }

    @GetMapping("/post/{postId}")
    public List<CommentDto> getCommentsByPostId(@PathVariable Long postId) {
        return commentRepository.findAllByPostIdWithUserInfo(postId);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateComment(@PathVariable Long id, @RequestBody Map<String, String> req) {
        String content = req.get("content");
        if (content == null || content.trim().isEmpty()) {
            return ResponseEntity.badRequest().body("內容不能為空");
        }
        Comment updated = commentService.updateComment(id, content);
        if (updated != null) {
            return ResponseEntity.ok(updated);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteComment(@PathVariable Long id) {
        commentService.deleteById(id);
        return ResponseEntity.ok("刪除成功！");
    }

}