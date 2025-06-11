package com.example.demo.dto;

import com.example.demo.entity.User;
import com.example.demo.enums.UserType;

public class UserDto {
    private Boolean idVerified;
    private Boolean emailVerified;
    private Long id;
    private String username;
    private String email;
    private String phoneNumber;
    private String location;
    private UserType userType;
    private String idNumber;
    private String role;
    private String bio;
    private String createdAt;
    private String avatarUrl;

    // Constructor，傳入 User entity 轉換
    public UserDto(User user) {
        this.id = user.getId();
        this.username = user.getUsername();
        this.email = user.getEmail();
        this.phoneNumber = user.getPhoneNumber();
        this.location = user.getLocation();
        this.userType = user.getUserType();
        this.idNumber = user.getIdNumber();
        this.role = user.getRole();
        this.idVerified = user.getIsIdVerified();
        this.emailVerified = user.getIsEmailVerified();
        this.bio = user.getBio();
        this.createdAt = user.getCreatedAt() != null ? user.getCreatedAt().toString() : null;
        // 頭像URL如有需要自行組合
        // this.avatarUrl = "/api/users/" + user.getId() + "/avatar";
    }

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getPhoneNumber() { return phoneNumber; }
    public void setPhoneNumber(String phoneNumber) { this.phoneNumber = phoneNumber; }

    public String getLocation() { return location; }
    public void setLocation(String location) { this.location = location; }

    public UserType getUserType() { return userType; }
    public void setUserType(UserType userType) { this.userType = userType; }

    public String getIdNumber() { return idNumber; }
    public void setIdNumber(String idNumber) { this.idNumber = idNumber; }

    public String getRole() { return role; }
    public void setRole(String role) { this.role = role; }

    public Boolean getIdVerified() { return idVerified; }
    public void setIdVerified(Boolean idVerified) { this.idVerified = idVerified; }

    public Boolean getEmailVerified() { return emailVerified; }
    public void setEmailVerified(Boolean emailVerified) { this.emailVerified = emailVerified; }

    public String getBio() { return bio; }
    public void setBio(String bio) { this.bio = bio; }

    public String getCreatedAt() { return createdAt; }
    public void setCreatedAt(String createdAt) { this.createdAt = createdAt; }

    public String getAvatarUrl() { return avatarUrl; }
    public void setAvatarUrl(String avatarUrl) { this.avatarUrl = avatarUrl; }


}