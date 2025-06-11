package com.example.demo.controller;

import com.example.demo.entity.User;
import com.example.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/admin")
public class AdminUserController {

    @Autowired
    private UserService userService;

    // 取得所有使用者，支援關鍵字搜尋
    @GetMapping("/users")
    public List<User> getAllUsers(@RequestParam(value = "q", required = false) String keyword) {
        List<User> allUsers = userService.getAllUsers();
        if (keyword == null || keyword.isEmpty()) {
            return allUsers;
        }
        String lower = keyword.toLowerCase();
        return allUsers.stream()
                .filter(u ->
                        (u.getUsername() != null && u.getUsername().toLowerCase().contains(lower)) ||
                        (u.getPhoneNumber() != null && u.getPhoneNumber().toLowerCase().contains(lower)) ||
                        (u.getEmail() != null && u.getEmail().toLowerCase().contains(lower))
                )
                .collect(Collectors.toList());
    }
}