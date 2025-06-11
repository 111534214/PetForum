package com.example.demo.controller;

import com.example.demo.dto.UserDto;
import com.example.demo.entity.User;
import com.example.demo.repository.UserRepository;
import com.example.demo.service.UserService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.util.Optional;
import java.io.IOException;
import java.util.Map;
import com.example.demo.repository.PostRepository;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.HttpStatus;
import com.example.demo.service.PostService;
import com.example.demo.service.CommentService;
import com.example.demo.service.LikeService;
import com.example.demo.service.FavoriteService;

@RestController
@RequestMapping("/api/users")
public class UserProfileController {

    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;

    // 假設已經有對應的 Service
    @Autowired
    private PostService postService;
    @Autowired
    private CommentService commentService;
    @Autowired
    private LikeService likeService;
    @Autowired
    private FavoriteService favoriteService;


    // ✅ 更新個人簡介 bio
    @PutMapping("/{id}/bio")
    public ResponseEntity<String> updateBio(@PathVariable Long id, @RequestBody Map<String, String> body) {
        System.out.println("收到簡介更新: " + body);
        String bio = body.get("bio");
        if (bio == null) bio = "";
        User user = userRepository.findById(id).orElseThrow();
        user.setBio(bio);
        userRepository.save(user);
        return ResponseEntity.ok("個人簡介已更新");
    }

    @GetMapping("/{id}/bio")
    public ResponseEntity<Map<String, String>> getBio(@PathVariable Long id) {
        User user = userRepository.findById(id).orElseThrow();
        return ResponseEntity.ok(Map.of("bio", user.getBio() == null ? "" : user.getBio()));
    }

    // 上傳頭像並儲存為 BLOB
    @PostMapping("/{id}/avatar")
    public ResponseEntity<String> uploadAvatar(@PathVariable Long id, @RequestParam("file") MultipartFile file) {
        System.out.println("接收到頭像檔案，content-type: " + file.getContentType());
        try {
            User user = userRepository.findById(id).orElseThrow();
            user.setAvatar(file.getBytes());
            user.setAvatarContentType(file.getContentType()); // ❗ 很重要，確保類型正確
            userRepository.save(user);
            return ResponseEntity.ok("頭像已更新！");
        } catch (IOException e) {
            return ResponseEntity.status(500).body("❗頭像更新失敗");
        }
    }





    @GetMapping("/{id}/avatar")
    public ResponseEntity<byte[]> getAvatar(@PathVariable Long id) {
        User user = userRepository.findById(id).orElseThrow();
        byte[] imageData = user.getAvatar();
        String contentType = user.getAvatarContentType();
        if (imageData == null || contentType == null) {
            return ResponseEntity.notFound().build();
        }
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.parseMediaType(contentType));
        return new ResponseEntity<>(imageData, headers, HttpStatus.OK);
    }



    // ✅ 更改暱稱
    @PutMapping("/{id}/username")
    public ResponseEntity<String> updateUsername(@PathVariable Long id, @RequestBody Map<String, String> body) {
        String username = body.get("username");

        if (username == null || username.trim().isEmpty()) {
            return ResponseEntity.badRequest().body("名稱不可為空");
        }

        Optional<User> existing = userRepository.findByUsername(username);
        if (existing.isPresent() && !existing.get().getId().equals(id)) {
            return ResponseEntity.badRequest().body("名稱已存在");
        }

        User user = userRepository.findById(id).orElseThrow();
        user.setUsername(username);
        userRepository.save(user);

        return ResponseEntity.ok("名稱更新成功");
    }

 // ✅ 登出（session 清除）
    @PostMapping("/logout")
    public ResponseEntity<String> logout(HttpSession session) {
        session.invalidate();
        return ResponseEntity.ok("已登出");
    }

    // 取得我的文章
    @GetMapping("/{id}/posts")
    public ResponseEntity<?> getUserPosts(@PathVariable Long id) {
        return ResponseEntity.ok(postService.findByUserId(id));
    }

    // 取得我的留言
    @GetMapping("/{id}/comments")
    public ResponseEntity<?> getUserComments(@PathVariable Long id) {
        return ResponseEntity.ok(commentService.findByUserId(id));
    }

    // 取得我的按讚
    @GetMapping("/{id}/likes")
    public ResponseEntity<?> getUserLikes(@PathVariable Long id) {
        return ResponseEntity.ok(likeService.findByUserId(id));
    }

    // 取得我的收藏
    @GetMapping("/{id}/favorites")
    public ResponseEntity<?> getUserFavorites(@PathVariable Long id) {
        return ResponseEntity.ok(favoriteService.findByUserId(id));
    }


    // UserController.java

    @GetMapping("/{id}")
    public ResponseEntity<UserDto> getUserById(@PathVariable Long id) {
        User user = userService.findById(id);
        if (user == null) {
            return ResponseEntity.notFound().build();
        }
        UserDto dto = new UserDto(user);
        return ResponseEntity.ok(dto);
    }

}
