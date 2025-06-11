package com.example.demo.controller;

import com.example.demo.entity.AdoptApplication;
import com.example.demo.repository.AdoptRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.transaction.annotation.Transactional;
//import jakarta.validation.Valid;
//import org.springframework.validation.BindingResult;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.sql.Timestamp;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.beans.factory.annotation.Value;


@RestController
@RequestMapping("/api/adopt/review") // 注意這個不同的路徑
@CrossOrigin(origins = "*")
public class AdoptReviewController {

    @Autowired
    private AdoptRepository adoptRepository;

    @Autowired
    private JavaMailSender mailSender;

    @Value("${spring.mail.username}") // 發件人郵箱
    private String senderEmail;

    private void sendEmail(String to, String subject, String body) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(senderEmail);
        message.setTo(to);
        message.setSubject(subject);
        message.setText(body);
        mailSender.send(message);
    }

    @GetMapping("/pending")
    public ResponseEntity<List<AdoptApplication>> getPendingApplications() {
        return ResponseEntity.ok(adoptRepository.findByStatus("pending"));
    }



    // 申請審核通過
    @PutMapping("/{id}/approve")
    @Transactional
    public ResponseEntity<?> approveApplication(@PathVariable Long id, @RequestParam String reviewer) {
        return adoptRepository.findById(id).map(application -> {
            application.setStatus("approved");
            application.setReviewedBy(reviewer);
            application.setReviewedAt(Timestamp.valueOf(LocalDateTime.now()));
            adoptRepository.saveAndFlush(application);

            // ✅ 發送電子郵件通知申請者
            sendEmail(application.getEmail(), "領養申請審核結果",
                    "親愛的 " + application.getName() + "，您的領養申請已通過！請與我們聯繫以完成後續流程。");

            return ResponseEntity.ok(Map.of("message", "申請已通過"));
        }).orElse(ResponseEntity.badRequest().body(Map.of("error", "找不到該申請")));
    }


    // 申請審核拒絕
    @PutMapping("/{id}/reject")
    @Transactional
    public ResponseEntity<?> rejectApplication(@PathVariable Long id, @RequestParam String reviewer) {
        return adoptRepository.findById(id).map(application -> {
            application.setStatus("rejected");
            application.setReviewedBy(reviewer);
            application.setReviewedAt(Timestamp.valueOf(LocalDateTime.now()));
            adoptRepository.saveAndFlush(application);

            // ✅ 發送電子郵件通知申請者
            sendEmail(application.getEmail(), "領養申請審核結果",
                    "親愛的 " + application.getName() + "，很抱歉，您的領養申請未能通過。如果有任何問題，請與我們聯繫。");

            return ResponseEntity.ok(Map.of("message", "申請已拒絕"));
        }).orElse(ResponseEntity.badRequest().body(Map.of("error", "找不到該申請")));
    }


    @GetMapping("/approved")
    public ResponseEntity<List<AdoptApplication>> getApprovedApplications() {
        return ResponseEntity.ok(adoptRepository.findByStatus("approved"));
    }

    @GetMapping("/rejected")
    public ResponseEntity<List<AdoptApplication>> getRejectedApplications() {
        return ResponseEntity.ok(adoptRepository.findByStatus("rejected"));
    }

    @GetMapping("/test-email")
    public ResponseEntity<String> testEmail() {
        try {
            sendEmail("111534216@stu.ukn.edu.tw", "測試郵件", "這是一封測試郵件，確認 SMTP 設定是否正確。");
            return ResponseEntity.ok("✅ 測試郵件發送成功");
        } catch (Exception e) {
            return ResponseEntity.status(500).body("❌ 郵件發送失敗：" + e.getMessage());
        }
    }


}

