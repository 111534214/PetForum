package com.example.demo.entity;

import jakarta.persistence.*;
import lombok.Data;
import com.example.demo.enums.UserType;

@Data
@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String username; // 使用者名稱（原nickname）

    @Column(nullable = false, unique = true)
    private String email;

    private Long userId;

//    private String nickname;

    @Column(nullable = false)
    private String password;

    private String phoneNumber;
    private String location;

    @Enumerated(EnumType.STRING) // 這行很重要，讓它存成字串 NORMAL / ADMIN
    private UserType userType;

    @Column(unique = true)
    private String idNumber;

    @Column(nullable = false)
    private String role = "USER"; // 預設為 USER

    private boolean isIdVerified = false;
    private boolean isEmailVerified = false;

    private String bio;

    @Column(nullable = false, updatable = false)
    private java.time.LocalDateTime createdAt = java.time.LocalDateTime.now();

    @Column(columnDefinition = "TEXT")
    private String about;

    @Lob
    @Column(name = "avatar", columnDefinition = "LONGBLOB")
    private byte[] avatar;

    @Column(name = "avatar_content_type")
    private String avatarContentType;

    public byte[] getAvatar() {
        return avatar;
    }

    public void setAvatar(byte[] avatar) {
        this.avatar = avatar;
    }

    public Long getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public Boolean getIsIdVerified() {
        return isIdVerified;
    }

    public Boolean getIsEmailVerified() {
        return isEmailVerified;
    }
}