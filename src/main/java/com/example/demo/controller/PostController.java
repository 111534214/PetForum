//package com.example.demo.controller;
//
//import com.example.demo.entity.Post;
//import com.example.demo.service.PostService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.*;
//import org.springframework.web.multipart.MultipartFile;
//import java.util.List;
//import java.io.File;
//import java.io.IOException;
//import java.nio.file.*;
//import java.time.LocalDateTime;
//import java.util.UUID;
//import org.springframework.http.HttpStatus;
//import java.util.Map;
//import java.util.HashMap;
//import com.example.demo.entity.User;
//import com.example.demo.dto.PostDto;
//import com.example.demo.repository.UserRepository;
//import java.util.Optional;
//
//
//@CrossOrigin(origins = {"*", "*"})
//@RestController
//@RequestMapping("/api/posts")
//public class PostController {
//
//    @Autowired
//    private PostService postService;
//
//    @Autowired
//    private UserRepository userRepository;
//
//
//    @PutMapping("/{id}")
//    public ResponseEntity<?> updatePost(@PathVariable Long id, @RequestBody PostDto postDto) {
//        // 這裡寫你的資料庫更新邏輯
//        // ...
//        return ResponseEntity.ok().build();
//    }
//
//    // 貼文發佈
////    @PostMapping
////    public ResponseEntity<String> createPost(@RequestBody Post post) {
////        try {
////            post.setCreatedAt(LocalDateTime.now());
////            post.setViews(0);
////            post.setLikes(0);
////            postService.save(post);
////            return ResponseEntity.ok("貼文已成功建立！");
////        } catch (Exception e) {
////            e.printStackTrace();
////            return ResponseEntity.status(500).body("發佈失敗：" + e.getMessage());
////        }
////    }
//
//    // 圖片上傳 (儲存於 uploads/images/)
//    @PostMapping("/upload-image")
//    public ResponseEntity<String> uploadImage(@RequestParam("file") MultipartFile file) {
//        try {
//            String fileName = System.currentTimeMillis() + "_" + file.getOriginalFilename();
//            Path uploadPath = Paths.get("uploads/images");
//
//            if (!Files.exists(uploadPath)) {
//                Files.createDirectories(uploadPath);
//            }
//
//            Path filePath = uploadPath.resolve(fileName);
//            Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);
//
//            return ResponseEntity.ok("/uploads/images/" + fileName); // 回傳前端用的路徑
//        } catch (IOException e) {
//            return ResponseEntity.status(500).body("圖片上傳失敗：" + e.getMessage());
//        }
//    }
//
//    @PostMapping("/upload-video")
//    public ResponseEntity<String> uploadVideo(@RequestParam("file") MultipartFile file) {
//        try {
//            String extension = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf("."));
//            String fileName = UUID.randomUUID().toString() + extension;
//            // 儲存到專案根目錄 uploads/videos/
//            String uploadDir = new File("uploads/videos/").getAbsolutePath();
//            File dir = new File(uploadDir);
//            if (!dir.exists()) dir.mkdirs();
//
//            File dest = new File(uploadDir, fileName);
//            file.transferTo(dest);
//
//            // 前端直接用這個網址即可
//            return ResponseEntity.ok("/videos/" + fileName);
//        } catch (IOException e) {
//            return ResponseEntity.status(500).body("影片上傳失敗：" + e.getMessage());
//        }
//    }
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
////    @PostMapping("/upload-video")
////    public ResponseEntity<String> uploadVideo(@RequestParam("file") MultipartFile file) {
////        try {
////            String fileName = System.currentTimeMillis() + "_" + file.getOriginalFilename();
////            Path uploadPath = Paths.get("uploads/videos");
////
////            if (!Files.exists(uploadPath)) {
////                Files.createDirectories(uploadPath);
////            }
////
////            Path filePath = uploadPath.resolve(fileName);
////            Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);
////
////            return ResponseEntity.ok("/uploads/videos/" + fileName);  // 給前端預覽用
////        } catch (IOException e) {
////            return ResponseEntity.status(500).body("影片上傳失敗：" + e.getMessage());
////        }
////    }
//
//    @PostMapping("/users/upload-avatar")
//    public ResponseEntity<String> uploadAvatar(@RequestParam("file") MultipartFile file) {
//        try {
//            String fileName = UUID.randomUUID() + "_" + file.getOriginalFilename();
//            String uploadDir = "uploads/avatars/";
//            File dir = new File(uploadDir);
//            if (!dir.exists()) dir.mkdirs();
//
//            File dest = new File(uploadDir, fileName);
//            file.transferTo(dest);
//
//            return ResponseEntity.ok("/uploads/avatars/" + fileName);
//        } catch (IOException e) {
//            return ResponseEntity.status(500).body("上傳失敗：" + e.getMessage());
//        }
//    }
//
//    @GetMapping("/all")
//    public ResponseEntity<List<Post>> getAllPosts() {
//        try {
//            List<Post> posts = postService.findAll();
//            return ResponseEntity.ok(posts);
//        } catch (Exception e) {
//            e.printStackTrace();
//            return ResponseEntity.status(500).build();
//        }
//    }
//
//    @GetMapping("/search")
//    public ResponseEntity<List<Post>> searchPosts(@RequestParam(required = false) String keyword) {
//        try {
//            List<Post> posts = postService.searchPosts(keyword);  // 呼叫 service 層的搜尋方法
//            return ResponseEntity.ok(posts);
//        } catch (Exception e) {
//            e.printStackTrace();
//            return ResponseEntity.status(500).build();
//        }
//    }
//
//    @GetMapping("/category/{category}")
//    public ResponseEntity<List<Post>> getPostsByCategory(@PathVariable String category,
//                                                         @RequestParam(defaultValue = "newest") String sort) {
//        return ResponseEntity.ok(postService.findByCategory(category, sort));
//    }
//
//    @GetMapping("/{id}")
//    public ResponseEntity<?> getPostById(@PathVariable Long id) {
//        Post post = postService.findById(id);
//        if (post == null) {
//            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("找不到文章");
//        }
//
//        // 用 Map 回傳自定義欄位
//        Map<String, Object> response = new HashMap<>();
//        response.put("id", post.getId());
//        response.put("title", post.getTitle());
//        response.put("content", post.getContent());
//        response.put("image_url", post.getImageUrl());
//        response.put("video_url", post.getVideoUrl());
//        response.put("created_at", post.getCreatedAt());
//        response.put("username", post.getUser() != null ? post.getUser().getUsername() : "未知作者");
//
//        return ResponseEntity.ok(response);
//    }
//
//    @PostMapping
//    public ResponseEntity<String> createPost(@RequestBody PostDto postDto) {
//        try {
//            User user = userRepository.findById(postDto.getUserId())
//                    .orElseThrow(() -> new RuntimeException("找不到使用者"));
//
//            Post post = new Post();
//            post.setUser(user); // ✅ 這一行非常關鍵
//
//            post.setTitle(postDto.getTitle());
//            post.setContent(postDto.getContent());
//            post.setCategory(postDto.getCategory());
//            post.setImageUrl(postDto.getImageUrl());
//            post.setVideoUrl(postDto.getVideoUrl());
//            post.setCreatedAt(LocalDateTime.now());
//            post.setViews(0);
//            post.setLikes(0);
//            postService.save(post);
//            return ResponseEntity.ok("貼文已成功建立！");
//        } catch (Exception e) {
//            e.printStackTrace();
//            return ResponseEntity.status(500).body("發佈失敗：" + e.getMessage());
//        }
//    }
//
//}














package com.example.demo.controller;

import com.example.demo.entity.Post;
import com.example.demo.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.util.List;
import java.io.File;
import java.io.IOException;
import java.nio.file.*;
import java.time.LocalDateTime;
import java.util.UUID;
import org.springframework.http.HttpStatus;
import java.util.Map;
import java.util.HashMap;
import com.example.demo.entity.User;
import com.example.demo.dto.PostDto;
import com.example.demo.repository.UserRepository;
import com.example.demo.service.CommentService;

@CrossOrigin(origins = {"*", "*"})
@RestController
@RequestMapping("/api/posts")
public class PostController {

    @Autowired
    private PostService postService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CommentService commentService;

    // 編輯貼文 (PUT)
    @PutMapping("/{id}")
    public ResponseEntity<?> updatePost(@PathVariable Long id, @RequestBody PostDto postDto) {
        try {
            Post post = postService.findById(id);
            if (post == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("找不到貼文");
            }

            // 只有作者本人能編輯，這裡你可以加驗證（選擇性）

            // 更新欄位
            post.setTitle(postDto.getTitle());
            post.setContent(postDto.getContent());
            post.setCategory(postDto.getCategory());
            post.setImageUrl(postDto.getImageUrl());
            post.setVideoUrl(postDto.getVideoUrl());
            post.setUpdatedAt(LocalDateTime.now()); // 如果你有 updatedAt 欄位

            postService.save(post); // 與新增共用同一個 save 方法

            return ResponseEntity.ok("貼文已成功更新！");
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body("更新失敗：" + e.getMessage());
        }
    }

    // 圖片上傳 (儲存於 uploads/images/)
    @PostMapping("/upload-image")
    public ResponseEntity<String> uploadImage(@RequestParam("file") MultipartFile file) {
        try {
            String fileName = System.currentTimeMillis() + "_" + file.getOriginalFilename();
            Path uploadPath = Paths.get("uploads/images");

            if (!Files.exists(uploadPath)) {
                Files.createDirectories(uploadPath);
            }

            Path filePath = uploadPath.resolve(fileName);
            Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);

            return ResponseEntity.ok("/uploads/images/" + fileName); // 回傳前端用的路徑
        } catch (IOException e) {
            return ResponseEntity.status(500).body("圖片上傳失敗：" + e.getMessage());
        }
    }

    @PostMapping("/upload-video")
    public ResponseEntity<String> uploadVideo(@RequestParam("file") MultipartFile file) {
        try {
            String extension = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf("."));
            String fileName = UUID.randomUUID().toString() + extension;
            // 儲存到專案根目錄 uploads/videos/
            String uploadDir = new File("uploads/videos/").getAbsolutePath();
            File dir = new File(uploadDir);
            if (!dir.exists()) dir.mkdirs();

            File dest = new File(uploadDir, fileName);
            file.transferTo(dest);

            // 前端直接用這個網址即可
            return ResponseEntity.ok("/videos/" + fileName);
        } catch (IOException e) {
            return ResponseEntity.status(500).body("影片上傳失敗：" + e.getMessage());
        }
    }

    @PostMapping("/users/upload-avatar")
    public ResponseEntity<String> uploadAvatar(@RequestParam("file") MultipartFile file) {
        try {
            String fileName = UUID.randomUUID() + "_" + file.getOriginalFilename();
            String uploadDir = "uploads/avatars/";
            File dir = new File(uploadDir);
            if (!dir.exists()) dir.mkdirs();

            File dest = new File(uploadDir, fileName);
            file.transferTo(dest);

            return ResponseEntity.ok("/uploads/avatars/" + fileName);
        } catch (IOException e) {
            return ResponseEntity.status(500).body("上傳失敗：" + e.getMessage());
        }
    }

    @GetMapping("/all")
    public ResponseEntity<List<Post>> getAllPosts() {
        try {
            List<Post> posts = postService.findAll();
            return ResponseEntity.ok(posts);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).build();
        }
    }

    @GetMapping("/search")
    public ResponseEntity<List<Post>> searchPosts(@RequestParam(required = false) String keyword) {
        try {
            List<Post> posts = postService.searchPosts(keyword);  // 呼叫 service 層的搜尋方法
            return ResponseEntity.ok(posts);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).build();
        }
    }

    @GetMapping("/category/{category}")
    public ResponseEntity<List<Post>> getPostsByCategory(@PathVariable String category,
                                                         @RequestParam(defaultValue = "newest") String sort) {
        return ResponseEntity.ok(postService.findByCategory(category, sort));
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getPostById(@PathVariable Long id) {
        Post post = postService.findById(id);
        if (post == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("找不到文章");
        }

        // 用 Map 回傳自定義欄位
        Map<String, Object> response = new HashMap<>();
        response.put("id", post.getId());
        response.put("title", post.getTitle());
        response.put("content", post.getContent());
        response.put("image_url", post.getImageUrl());
        response.put("video_url", post.getVideoUrl());
        response.put("created_at", post.getCreatedAt());
        response.put("username", post.getUser() != null ? post.getUser().getUsername() : "未知作者");

        return ResponseEntity.ok(response);
    }

    @PostMapping
    public ResponseEntity<String> createPost(@RequestBody PostDto postDto) {
        try {
            User user = userRepository.findById(postDto.getUserId())
                    .orElseThrow(() -> new RuntimeException("找不到使用者"));

            Post post = new Post();
            post.setUser(user);

            post.setTitle(postDto.getTitle());
            post.setContent(postDto.getContent());
            post.setCategory(postDto.getCategory());
            post.setImageUrl(postDto.getImageUrl());
            post.setVideoUrl(postDto.getVideoUrl());
            post.setCreatedAt(LocalDateTime.now());
            post.setViews(0);
            post.setLikes(0);
            postService.save(post);
            return ResponseEntity.ok("貼文已成功建立！");
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body("發佈失敗：" + e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deletePost(@PathVariable Long id) {
        postService.deleteById(id);
        return ResponseEntity.ok("刪除成功！");
    }


    @DeleteMapping("/comments/{id}")
    public ResponseEntity<?> deleteComment(@PathVariable Long id) {
        commentService.deleteById(id);
        return ResponseEntity.ok("刪除成功！");
    }


    @GetMapping("")
    public List<Post> getAll() {
        return postService.getAll();
    }

}