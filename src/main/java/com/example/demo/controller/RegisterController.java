package com.example.demo.controller;

import com.example.demo.entity.User;
import com.example.demo.service.UserService;
import com.example.demo.enums.RegisterResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.example.demo.enums.UserType;
import java.util.Map;

@RestController
@RequestMapping("/api/auth")
public class RegisterController {

    @Autowired
    private UserService userService;

    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody Map<String, String> formData) {
        User user = new User();
        user.setUsername(formData.get("username"));
        user.setEmail(formData.get("email"));
        user.setPassword(formData.get("password"));
        user.setPhoneNumber(formData.get("phoneNumber"));
        user.setLocation(formData.get("location"));
        user.setIdNumber(formData.get("idNumber"));
        user.setBio(formData.getOrDefault("bio", ""));
        user.setUserType(UserType.NORMAL);

        String confirmPassword = formData.get("confirmPassword");

        RegisterResult result = userService.register(user, confirmPassword);
        switch (result) {
            case SUCCESS:
                return ResponseEntity.ok("註冊成功");
            case PASSWORD_MISMATCH:
                return ResponseEntity.badRequest().body("兩次密碼不一致");
            case USERNAME_EXISTS:
                return ResponseEntity.status(HttpStatus.CONFLICT).body("暱稱已被使用");
            case EMAIL_EXISTS:
                return ResponseEntity.status(HttpStatus.CONFLICT).body("信箱已被使用");
            case PHONE_EXISTS:
                return ResponseEntity.status(HttpStatus.CONFLICT).body("電話已被使用");
            case ID_EXISTS:
                return ResponseEntity.status(HttpStatus.CONFLICT).body("身分證已被使用");
            default:
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("註冊失敗");
        }
    }

}
