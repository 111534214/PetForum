package com.example.demo.service;

import com.example.demo.entity.User;
import com.example.demo.enums.RegisterResult;
import com.example.demo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;




    // 註冊功能：驗證欄位重複與密碼一致性
    public RegisterResult register(User user, String confirmPassword) {
        if (confirmPassword == null || !confirmPassword.equals(user.getPassword())) {
            return RegisterResult.PASSWORD_MISMATCH;
        }
        if (userRepository.existsByUsername(user.getUsername())) {
            return RegisterResult.USERNAME_EXISTS;
        }
        if (userRepository.existsByEmail(user.getEmail())) {
            return RegisterResult.EMAIL_EXISTS;
        }
        if (userRepository.existsByPhoneNumber(user.getPhoneNumber())) {
            return RegisterResult.PHONE_EXISTS;
        }
        if (userRepository.existsByIdNumber(user.getIdNumber())) {
            return RegisterResult.ID_EXISTS;
        }

        // 明文密碼儲存（不加密）
        user.setPassword(user.getPassword());
        userRepository.save(user);
        return RegisterResult.SUCCESS;
    }

    // 登入功能：email 或 身分證驗證密碼（明文比對）
    public boolean login(String account, String rawPassword) {
        Optional<User> userOpt = userRepository.findByEmail(account);
        if (userOpt.isEmpty()) {
            userOpt = userRepository.findByIdNumber(account);
        }
        if (userOpt.isEmpty()) {
            return false;
        }

        User user = userOpt.get();
        return rawPassword.equals(user.getPassword()); // 明文比對
    }

    // 查詢是否存在
    public boolean emailExists(String email) {
        return userRepository.existsByEmail(email);
    }

    public boolean usernameExists(String username) {
        return userRepository.existsByUsername(username);
    }

    // 找使用者 by email
    public User findByEmail(String email) {
        return userRepository.findByEmail(email).orElse(null);
    }

    // 重設密碼（明文儲存）
    public boolean resetPassword(String email, String idNumber, String newPassword) {
        User user = userRepository.findByEmail(email).orElse(null);
        if (user == null || !user.getIdNumber().equals(idNumber)) {
            return false;
        }

        user.setPassword(newPassword); // 明文儲存
        userRepository.save(user);
        return true;
    }

    // 用帳號 (email or idNumber) 找使用者
    public User findUserByAccount(String account) {
        return userRepository.findByEmailOrIdNumber(account, account).orElse(null);
    }

    // 更新個人簡介
    public void updateAbout(Long id, String about) {
        User user = userRepository.findById(id).orElseThrow();
        user.setAbout(about);
        userRepository.save(user);
    }

    public String getAbout(Long id) {
        User user = userRepository.findById(id).orElseThrow();
        return user.getAbout();
    }

    // 更新暱稱
    public boolean updateUsername(Long id, String newUsername) {
        if (userRepository.existsByUsername(newUsername)) {
            return false;
        }
        User user = userRepository.findById(id).orElseThrow();
        user.setUsername(newUsername);
        userRepository.save(user);
        return true;
    }

    // 更新頭像圖片
    public void updateAvatar(Long id, byte[] imageBytes) {
        User user = userRepository.findById(id).orElseThrow();
        user.setAvatar(imageBytes);
        userRepository.save(user);
    }

    public boolean existsByUsername(String username) {
        return userRepository.findByUsername(username).isPresent();
    }

    public boolean existsByEmail(String email) {
        return userRepository.findByEmail(email).isPresent();
    }

    public boolean existsByPhoneNumber(String phoneNumber) {
        return userRepository.findByPhoneNumber(phoneNumber).isPresent();
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }


    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }

    public User findById(Long id) {
        // 會回傳 Optional<User>
        return userRepository.findById(id).orElse(null);
    }
}
