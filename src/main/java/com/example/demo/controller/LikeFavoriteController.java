package com.example.demo.controller;

import com.example.demo.entity.Like;
import com.example.demo.entity.Favorite;
import com.example.demo.repository.FavoriteRepository;
import com.example.demo.repository.UserRepository;
import com.example.demo.repository.PostRepository;
import com.example.demo.repository.LikeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api")
public class LikeFavoriteController {

    @Autowired
    private LikeRepository likeRepo;

    @Autowired
    private FavoriteRepository favRepo;

    @Autowired
    private UserRepository userRepository;  // 🔥 補上 UserRepository

    @Autowired
    private PostRepository postRepository;  // 🔥 補上 PostRepository

    // 切換按讚
    @PostMapping("/like")
    public ResponseEntity<?> toggleLike(@RequestParam Long userId, @RequestParam Long postId) {
        if (!userRepository.existsById(userId)) {
            return ResponseEntity.badRequest().body(Map.of("error", "User not found"));
        }
        if (!postRepository.existsById(postId)) {
            return ResponseEntity.badRequest().body(Map.of("error", "Post not found"));
        }
        Optional<Like> existing = likeRepo.findByUserIdAndPostId(userId, postId);
        if (existing.isPresent()) {
            likeRepo.delete(existing.get());
            return ResponseEntity.ok(Map.of("status", "unliked"));
        } else {
            Like like = new Like(userId, postId);  // 🔥 記得在 Like Entity 中建構子
            likeRepo.save(like);
            return ResponseEntity.ok(Map.of("status", "liked"));
        }
    }

    // 切換收藏
    @PostMapping("/favorite")
    public ResponseEntity<?> toggleFavorite(@RequestParam Long userId, @RequestParam Long postId) {
        if (!userRepository.existsById(userId)) {
            return ResponseEntity.badRequest().body(Map.of("error", "User not found"));
        }
        if (!postRepository.existsById(postId)) {
            return ResponseEntity.badRequest().body(Map.of("error", "Post not found"));
        }
        Optional<Favorite> existing = favRepo.findByUserIdAndPostId(userId, postId);
        if (existing.isPresent()) {
            favRepo.delete(existing.get());
            return ResponseEntity.ok(Map.of("status", "unfavorited"));
        } else {
            Favorite favorite = new Favorite(userId, postId);
            favRepo.save(favorite);
            return ResponseEntity.ok(Map.of("status", "favorited"));
        }
    }


    // 查詢按讚與收藏狀態
    @GetMapping("/status")
    public ResponseEntity<?> getStatus(@RequestParam Long userId, @RequestParam Long postId) {
        boolean liked = likeRepo.findByUserIdAndPostId(userId, postId).isPresent();
        boolean favorited = favRepo.findByUserIdAndPostId(userId, postId).isPresent();
        return ResponseEntity.ok(Map.of("liked", liked, "favorited", favorited));
    }

    @GetMapping("/like/status")
    public Map<String, Object> checkLikeStatus(@RequestParam Long userId, @RequestParam Long postId) {
        boolean liked = likeRepo.existsByUserIdAndPostId(userId, postId);
        return Map.of("liked", liked);
    }

    @GetMapping("/favorite/status")
    public Map<String, Object> checkFavoriteStatus(@RequestParam Long userId, @RequestParam Long postId) {
        boolean favorited = favRepo.existsByUserIdAndPostId(userId, postId);
        return Map.of("favorited", favorited);
    }

}
