package com.example.demo.entity;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "news")
public class News {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;         // 標題

    @Column(columnDefinition = "TEXT", nullable = false)
    private String content;       // 內文

    @Column(nullable = false)
    private String category;      // 分類

    @Column(name = "image_url")
    private String imageUrl;      // 圖片

    @Column(name = "video_url")
    private String videoUrl;      // 影片

    @Column(name = "publish_date")
    private LocalDateTime publishDate; // 發布日期

    @Column(name = "is_featured")
    private Boolean isFeatured = false; // 精選

    // 可加 getter/setter 如需自訂
}