package com.example.demo.service;

import java.util.List;
import com.example.demo.entity.Post;
import com.example.demo.repository.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PostService {

    @Autowired
    private PostRepository postRepository;

    // 儲存貼文
    public void save(Post post) {
        postRepository.save(post);
    }

    // 查找所有貼文
    public List<Post> findAll() {
        return postRepository.findAll();
    }

    // 根據類別查找貼文並附帶用戶資訊
    public List<Post> findByCategory(String category) {
        return postRepository.findByCategoryWithUser(category);  // 確保這個方法存在於 repository
    }


    public List<Post> findByCategory(String category, String sort) {
        if ("newest".equals(sort)) {
            return postRepository.findByCategoryOrderByCreatedAtDesc(category);
        } else if ("likes".equals(sort)) {
            return postRepository.findByCategoryOrderByLikesDesc(category);
        } else {
            return postRepository.findByCategory(category);
        }
    }

    // 根據 postId 查找貼文
    public Post findById(Long postId) {
        return postRepository.findById(postId).orElse(null);
//        return postRepository.findById(id).orElse(null);
    }

    // 增加按讚數
    public void incrementLikes(Long postId) {
        Post post = postRepository.findById(postId).orElseThrow(() -> new RuntimeException("Post not found"));
        post.setLikes(post.getLikes() + 1);
        postRepository.save(post);
    }

    // 根據類別及排序方式查找貼文
    public List<Post> findByCategorySorted(String category, String sortType) {
        // 先查詢所有貼文（可以加上是否需要用戶資訊的標誌）
        List<Post> posts = findByCategory(category);

        // 根據 sortType 進行排序
        posts.sort((p1, p2) -> {
            switch (sortType) {
                case "likes":
                    return Integer.compare(p2.getLikes(), p1.getLikes());
                case "comments":
                    return Integer.compare(p2.getCommentCount(), p1.getCommentCount());
                case "newest":
                default:
                    return p2.getCreatedAt().compareTo(p1.getCreatedAt());
            }
        });

        return posts;
    }

    // 根據關鍵字搜尋貼文
    public List<Post> searchPosts(String keyword) {
        if (keyword == null || keyword.trim().isEmpty()) {
            return postRepository.findAll();  // 如果沒有關鍵字，則返回所有文章
        } else {
            return postRepository.searchPostsByKeyword("%" + keyword + "%");  // 查詢匹配的文章
        }
    }

    // PostService.java
    public List<Post> findByUserId(Long userId) {
        return postRepository.findByUserId(userId);
    }

    public void deleteById(Long postId) {
        postRepository.deleteById(postId);
    }

    public List<Post> getAll() {
        return postRepository.findAll();
    }

}
