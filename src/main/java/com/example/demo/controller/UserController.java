package com.example.demo.controller;

import com.example.demo.entity.User;
import com.example.demo.service.UserService;
import com.example.demo.service.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.HashMap;
import java.util.Map;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.http.HttpStatus;


@RestController
@RequestMapping("/api/auth")
public class UserController {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserService userService;

    @Autowired
    private EmailService emailService;

@PostMapping("/login")
public ResponseEntity<?> login(@RequestBody Map<String, String> request) {
    String account = request.get("account");
    String password = request.get("password");

    // 從 service 查找 user（支援 email 或 idNumber）
    User user = userService.findUserByAccount(account);

    if (user == null) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Map.of("message", "帳號不存在"));
    }

    // 使用 bcrypt 密碼加密比對
    if (!user.getPassword().equals(password)) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Map.of("message", "密碼錯誤"));
    }


    // 登入成功
    Map<String, Object> response = new HashMap<>();
    response.put("message", "登入成功");
    response.put("username", user.getUsername());
    response.put("userId", user.getId());
    return ResponseEntity.ok(response);
}

    @GetMapping("/check-email")
    public boolean checkEmailExists(@RequestParam String email) {
        return userService.emailExists(email);
    }


    @PostMapping("/send-reset-link")
    public ResponseEntity<String> sendResetLink(@RequestBody Map<String, String> data) {
        String email = data.get("email");

        User user = userService.findByEmail(email);
        if (user == null) {
            return ResponseEntity.status(404).body("找不到該使用者");
        }

        // 建立假設的重設連結（正式應該要產生 token）
        String resetLink = "http://localhost:8080/reset-password?email=" + email;
        emailService.sendResetPasswordEmail(email, resetLink);

        return ResponseEntity.ok("重設密碼連結已寄出到信箱！");
    }

    @PostMapping("/reset-password")
    public ResponseEntity<String> resetPassword(@RequestBody Map<String, String> data) {
        String email = data.get("email");
        String idNumber = data.get("idNumber");
        String newPassword = data.get("newPassword");

        boolean success = userService.resetPassword(email, idNumber, newPassword);
        return success ?
                ResponseEntity.ok("密碼已更新") :
                ResponseEntity.status(404).body("找不到帳號或身分證不符");
    }

    @GetMapping("/users")
    public ResponseEntity<?> getAllUsers() {
        return ResponseEntity.ok(userService.getAllUsers());
    }

}