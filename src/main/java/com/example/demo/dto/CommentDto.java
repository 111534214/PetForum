//package com.example.demo.dto;
//
//import java.time.LocalDateTime;
//
//public class CommentDto {
//    private Long id;
//    private Long postId;
//    private Long userId;
//    private String username;
//    private String content;
//    private LocalDateTime createdAt;
//    private String avatarUrl;
//    private String postTitle;
//
//    // 無參數建構子（Jackson 需要）
//    public CommentDto() {
//    }
//
//    // 全欄位建構子
//    public CommentDto(Long id, Long postId, Long userId, String content,
//                      LocalDateTime createdAt, String username) {
//        this.id = id;
//        this.postId = postId;
//        this.userId = userId;
//        this.content = content;
//        this.createdAt = createdAt;
//        this.username = username;
//        this.avatarUrl = "/api/users/" + userId + "/avatar";
//        this.postTitle = postTitle;
//    }
//
//    // 簡化 Constructor（如只需部分欄位）
//    public CommentDto(Long id, String username, String content, LocalDateTime createdAt) {
//        this.id = id;
//        this.username = username;
//        this.content = content;
//        this.createdAt = createdAt;
//        // 若只用這個建構子，建議補 userId 給 avatarUrl
//        this.avatarUrl = null; // 預設為 null，需要時請補 userId 組成
//    }
//
//    // Getter & Setter
//
//    public Long getId() {
//        return id;
//    }
//    public void setId(Long id) {
//        this.id = id;
//    }
//
//    public Long getPostId() {
//        return postId;
//    }
//    public void setPostId(Long postId) {
//        this.postId = postId;
//    }
//
//    public Long getUserId() {
//        return userId;
//    }
//    public void setUserId(Long userId) {
//        this.userId = userId;
//    }
//
//    public String getUsername() {
//        return username;
//    }
//    public void setUsername(String username) {
//        this.username = username;
//    }
//
//    public String getContent() {
//        return content;
//    }
//    public void setContent(String content) {
//        this.content = content;
//    }
//
//    public LocalDateTime getCreatedAt() {
//        return createdAt;
//    }
//    public void setCreatedAt(LocalDateTime createdAt) {
//        this.createdAt = createdAt;
//    }
//
//    public String getAvatarUrl() {
//        // 若 avatarUrl 尚未設定且 userId 有值時自動組合
//        if (avatarUrl == null && userId != null) {
//            return "/api/users/" + userId + "/avatar";
//        }
//        return avatarUrl;
//    }
//    public void setAvatarUrl(String avatarUrl) {
//        this.avatarUrl = avatarUrl;
//    }
//
//    public String getPostTitle() {
//        return postTitle;
//    }
//    public void setPostTitle(String postTitle) {
//        this.postTitle = postTitle;
//    }
//}


package com.example.demo.dto;

import java.time.LocalDateTime;

public class CommentDto {
    private Long id;
    private Long postId;
    private Long userId;
    private String username;
    private String content;
    private LocalDateTime createdAt;
    private String avatarUrl;
    private String postTitle; // 新增：貼文標題

    // 無參數建構子（Jackson 需要）
    public CommentDto() {
    }

    // 全欄位建構子（含 postTitle）
    public CommentDto(Long id, Long postId, Long userId, String content,
                      LocalDateTime createdAt, String username) {
        this.id = id;
        this.postId = postId;
        this.userId = userId;
        this.content = content;
        this.createdAt = createdAt;
        this.username = username;
        this.avatarUrl = "/api/users/" + userId + "/avatar";
        // 這裡 postTitle 可以不用給，或設為 null

        this.postTitle = null;
    }

    // 其他建構子（如只需部分欄位）
    public CommentDto(Long id, String username, String content, LocalDateTime createdAt) {
        this.id = id;
        this.username = username;
        this.content = content;
        this.createdAt = createdAt;
        this.avatarUrl = null;
    }

    // Getter & Setter

    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }

    public Long getPostId() {
        return postId;
    }
    public void setPostId(Long postId) {
        this.postId = postId;
    }

    public Long getUserId() {
        return userId;
    }
    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }
    public void setUsername(String username) {
        this.username = username;
    }

    public String getContent() {
        return content;
    }
    public void setContent(String content) {
        this.content = content;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public String getAvatarUrl() {
        if (avatarUrl == null && userId != null) {
            return "/api/users/" + userId + "/avatar";
        }
        return avatarUrl;
    }
    public void setAvatarUrl(String avatarUrl) {
        this.avatarUrl = avatarUrl;
    }

    // 新增 Getter/Setter for postTitle
    public String getPostTitle() {
        return postTitle;
    }
    public void setPostTitle(String postTitle) {
        this.postTitle = postTitle;
    }

    private LocalDateTime updatedAt;

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }
    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }
}
