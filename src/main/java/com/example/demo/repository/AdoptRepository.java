package com.example.demo.repository;

import com.example.demo.entity.AdoptApplication;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface AdoptRepository extends JpaRepository<AdoptApplication, Long> {

    // 查詢所有尚未審核的申請
    List<AdoptApplication> findByStatus(String status);

    // 查詢特定 pet 的所有申請（可選）
    List<AdoptApplication> findByPetId(Long petId);

    // 查詢特定審核狀態且為某隻 pet 的申請（可選）
    List<AdoptApplication> findByPetIdAndStatus(Long petId, String status);
}
