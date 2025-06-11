package com.example.demo.controller;

import com.example.demo.entity.News;
import com.example.demo.service.NewsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.*;

@CrossOrigin(origins = {"*", "*"})
@RestController
@RequestMapping("/api/news")
public class NewsController {

    @Autowired
    private NewsService newsService;

    // 新增新聞
    @PostMapping
    public ResponseEntity<String> createNews(@RequestBody News news) {
        news.setPublishDate(LocalDateTime.now());
        newsService.save(news);
        return ResponseEntity.ok("新聞已成功建立！");
    }

    // 刪除新聞
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteNews(@PathVariable Long id) {
        newsService.deleteById(id);
        return ResponseEntity.ok("刪除成功！");
    }

    // 所有新聞列表
    @GetMapping("/all")
    public ResponseEntity<List<News>> getAllNews() {
        return ResponseEntity.ok(newsService.findAll());
    }

    // 依分類查詢
    @GetMapping("/category/{category}")
    public ResponseEntity<List<News>> getNewsByCategory(@PathVariable String category) {
        return ResponseEntity.ok(newsService.findByCategory(category));
    }

    // 精選新聞
    @GetMapping("/featured")
    public ResponseEntity<List<News>> getFeaturedNews() {
        return ResponseEntity.ok(newsService.findFeatured());
    }

    // 查詢單一新聞（詳情）
    @GetMapping("/{id}")
    public ResponseEntity<?> getNewsById(@PathVariable Long id) {
        News news = newsService.findById(id);
        if (news == null) {
            return ResponseEntity.status(404).body("找不到新聞");
        }
        // 只要自定義欄位就用 Map
        Map<String, Object> response = new HashMap<>();
        response.put("id", news.getId());
        response.put("title", news.getTitle());
        response.put("content", news.getContent());
        response.put("image_url", news.getImageUrl());
        response.put("video_url", news.getVideoUrl());
        response.put("category", news.getCategory());
        response.put("publish_date", news.getPublishDate());
        response.put("is_featured", news.getIsFeatured());

        return ResponseEntity.ok(response);
    }
}