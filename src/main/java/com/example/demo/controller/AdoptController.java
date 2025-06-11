package com.example.demo.controller;

import com.example.demo.entity.AdoptApplication;
import com.example.demo.repository.AdoptRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.transaction.annotation.Transactional;
import jakarta.validation.Valid;
import org.springframework.validation.BindingResult;


@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "*") // 如果需要跨域
public class AdoptController {

    @Autowired
    private AdoptRepository adoptRepository;


    @PostMapping("/adopt")
    @Transactional
    public ResponseEntity<?> createApplication(@Valid @RequestBody AdoptApplication application,
                                               BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            String errorMessage = bindingResult.getFieldErrors().stream()
                    .map(e -> e.getDefaultMessage())
                    .findFirst()
                    .orElse("表單驗證失敗");
            return ResponseEntity.badRequest().body(java.util.Map.of("error", errorMessage));
        }

        System.out.println("收到申請資料：" + application);
        adoptRepository.save(application);
        adoptRepository.flush();
        return ResponseEntity.ok().body(java.util.Map.of("message", "申請成功"));
    }




}
